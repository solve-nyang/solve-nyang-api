package com.ssafy.solvedpick.auth.service;

import com.ssafy.solvedpick.auth.domain.VerificationKey;
import com.ssafy.solvedpick.auth.dto.ChangePasswordDTO;
import com.ssafy.solvedpick.auth.dto.UserDataDTO;
import com.ssafy.solvedpick.auth.dto.TokenResponse;
import com.ssafy.solvedpick.auth.dto.UsernameResponse;
import com.ssafy.solvedpick.auth.repository.VerificationKeyRepository;
import com.ssafy.solvedpick.avatars.domain.Avatar;
import com.ssafy.solvedpick.avatars.repository.AvatarRepository;
import com.ssafy.solvedpick.common.error.exception.InvalidPasswordException;
import com.ssafy.solvedpick.common.error.exception.UserInfoErrorException;
import com.ssafy.solvedpick.common.error.exception.VerificationNotFoundException;
import com.ssafy.solvedpick.common.jwt.JwtUtil;
import com.ssafy.solvedpick.facade.UserFacade;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.members.repository.MemberRepository;
import com.ssafy.solvedpick.ownedavatar.domain.OwnedAvatar;
import com.ssafy.solvedpick.ownedavatar.repository.OwnedAvatarRepository;
import com.ssafy.solvedpick.ownedbackgrounds.facade.OwnedBackgroundFacade;

import jakarta.servlet.http.HttpServletResponse;
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
    private final AvatarRepository avatarRepository;
    private final OwnedAvatarRepository ownedAvatarRepository;
    private final UserFacade userFacade;
    private final OwnedBackgroundFacade ownedBackgroundFacade;

    @Value("${URL.USER_INFO}")
    private String url;

    public TokenResponse signIn(UserDataDTO userDataDTO, HttpServletResponse response) {
        Member member = memberRepository.findByUsername(userDataDTO.getUsername())
                .orElseThrow(() -> new UserInfoErrorException("아이디가 잘못되었습니다. 다시 확인해주세요."));

        if (!passwordEncoder.matches(userDataDTO.getPassword(), member.getPassword())) {
            throw new UserInfoErrorException("비밀번호가 잘못되었습니다. 다시 확인해주세요.");
        }

        String accessToken = jwtUtil.generateAccessToken(userDataDTO.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(userDataDTO.getUsername());
        
        jwtUtil.addRefreshTokenToCookie(response, refreshToken);
        return TokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    @Transactional
    public Member create(UserDataDTO userDataDTO) {
        Member user = Member.builder()
                .username(userDataDTO.getUsername())
                .password(passwordEncoder.encode(userDataDTO.getPassword()))
                .build();

        this.memberRepository.save(user);

        ownedBackgroundFacade.addDefaultBackground(user);

        userFacade.initializeNewUserInfo(user);

        return user;
    }

    private void addDefaultAvatar(Member member) {
        Avatar defaultAvatar = avatarRepository.findByName("MVPCat");

        OwnedAvatar ownedAvatar = OwnedAvatar.builder()
                .member(member)
                .avatar(defaultAvatar)
                .visible(false)
                .build();

        ownedAvatarRepository.save(ownedAvatar);
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

    public boolean verifyUser(UserDataDTO userDataDTO) {
        String username = userDataDTO.getUsername();

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
            return false;
        }
    }

    public Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        log.debug("userName: {}", username);
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
    }
    
    @Transactional
    public void changePassword(ChangePasswordDTO changePasswordDTO) {
        Member member = getCurrentMember();
        String currentPassword = changePasswordDTO.getCurrentPassword();
        String newPassword = changePasswordDTO.getNewPassword();

        if (!passwordEncoder.matches(currentPassword, member.getPassword())) {
            throw new InvalidPasswordException("현재 비밀번호가 잘못되었습니다.");
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        member.updatePassword(encodedNewPassword);
    }
    
    @Transactional
    public void findPassword(UserDataDTO userDataDTO) {
        Member member = memberRepository.findByUsername(userDataDTO.getUsername())
            .orElseThrow(() -> new UserInfoErrorException("존재하지 않는 유저입니다."));
        boolean verified = verifyUser(userDataDTO);
        if (verified) {
            String newPassword = passwordEncoder.encode(userDataDTO.getPassword());
            member.updatePassword(newPassword);
        } else {
            throw new VerificationNotFoundException("solved.ac에 암호화 키를 잘 저장하였는지 확인하세요.");
        }
    }
    
    public void isValidPassword(String password) {
    	if (password == null || password.length() < 8) {
    		throw new InvalidPasswordException("비밀번호는 8자 이상이어야 합니다.");
    	}
    	
    	if (password.matches(".*\\s+.*")) {
    		throw new InvalidPasswordException("비밀번호에 공백을 포함할 수 없습니다.");
    	}
    	
    	if (password.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣].*")) {
    		throw new InvalidPasswordException("비밀번호에 한글을 포함할 수 없습니다.");
    	}
    	
    	String specialChars = "[~!@#$%^&*_+=`|\\\\(){},.\\/\\[\\]\\-:;\"'<>?]";
    	String alphabet = "[a-zA-Z]";
    	String numbers = "[0-9]";
    	
    	boolean hasSpecialChar = password.matches(".*" + specialChars + ".*");
    	boolean hasAlphabet = password.matches(".*" + alphabet + ".*");
    	boolean hasNumber = password.matches(".*" + numbers + ".*");
    	
    	if (!(hasSpecialChar && hasAlphabet && hasNumber)) {
            throw new InvalidPasswordException("비밀번호는 영문자, 숫자, 특수문자를 모두 포함해야 합니다.");
        }
    }
}
