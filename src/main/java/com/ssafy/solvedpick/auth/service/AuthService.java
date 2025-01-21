package com.ssafy.solvedpick.auth.service;

import com.ssafy.solvedpick.auth.domain.VerificationKey;
import com.ssafy.solvedpick.auth.dto.SignInFormDTO;
import com.ssafy.solvedpick.auth.dto.SignupFormDTO;
import com.ssafy.solvedpick.auth.dto.TokenResponse;
import com.ssafy.solvedpick.auth.dto.UsernameResponse;
import com.ssafy.solvedpick.auth.repository.VerificationKeyRepository;
import com.ssafy.solvedpick.global.error.exception.UserInfoErrorException;
import com.ssafy.solvedpick.global.error.exception.VerificationNotFoundException;
import com.ssafy.solvedpick.jwt.JwtUtil;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.members.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate;
    private final VerificationKeyRepository verificationKeyRepository;

    @Value("${URL.USER_INFO}")
    private String url;

    public TokenResponse signIn(SignInFormDTO signInFormDTO) {
        Member member = memberRepository.findByUsername(signInFormDTO.getUsername())
                .orElseThrow(() -> new UserInfoErrorException("Login Error"));

        if (!passwordEncoder.matches(signInFormDTO.getPassword(), member.getPassword())) {
            throw new UserInfoErrorException("Login Error");
        }

        String accessToken = jwtUtil.generateAccessToken(signInFormDTO.getUsername());

        TokenResponse tokenResponse = TokenResponse.builder()
                .accessToken(accessToken)
                .build();
        return tokenResponse;
    }

    public Member create(SignupFormDTO signupFormDTO) {
        Member user = Member.builder()
                .username(signupFormDTO.getUsername())
                .password(passwordEncoder.encode(signupFormDTO.getPassword()))
                .build();

        if (memberRepository.existsByUsername(signupFormDTO.getUsername())) {
            return user;
        }

        this.memberRepository.save(user);
        return user;
    }

    @Transactional
    public String generateVerificationCode(String username) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom secureRandom = new SecureRandom();

        StringBuilder code = new StringBuilder(12);
        for (int i = 0; i < 12; i++) {
            code.append(characters.charAt(secureRandom.nextInt(characters.length())));
        }
        String verificationCode = code.toString();

        verificationKeyRepository.findByUsername(username)
                .ifPresentOrElse(
                        key -> key.updateVerificationCode(verificationCode),
                        () -> {
                            VerificationKey newKey = VerificationKey.builder()
                                    .username(username)
                                    .verificationCode(verificationCode)
                                    .createdAt(LocalDateTime.now())
                                    .build();
                            verificationKeyRepository.save(newKey);
                        }
                );

        return verificationCode;
    }

    public boolean checkUser(String username) {
        try {
            log.debug("check user");
            restTemplate.getForEntity(url + username, UsernameResponse.class);
            return true;
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            return false;
        }
    }

    public boolean verifyUser(SignupFormDTO signupFormDTO) {
        String username = signupFormDTO.getUsername();

        try {
            VerificationKey verificationKey = verificationKeyRepository.findByUsername(username)
                    .orElseThrow(() -> new VerificationNotFoundException("Verification key not found for username: " + username));
            log.debug("code: {}", verificationKey.getVerificationCode());

            ResponseEntity<UsernameResponse> response =
                    restTemplate.getForEntity(url + username, UsernameResponse.class);

            return response.getBody() != null
                    && verificationKey.getVerificationCode().equals(response.getBody().getName());
        } catch (HttpClientErrorException.NotFound e) {
            log.error("user not found");
            return false;
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException("Failed to verify user");
        }
    }

    public Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        log.debug("userName: {}", username);
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }
}
