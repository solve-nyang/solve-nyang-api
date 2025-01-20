package com.ssafy.solvedpick.accounts.service;

import com.ssafy.solvedpick.accounts.dto.SignInFormDTO;
import com.ssafy.solvedpick.jwt.JwtUtil;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.members.repository.MemberRepository;
import com.ssafy.solvedpick.accounts.dto.TokenResponse;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SigninService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public TokenResponse signIn(SignInFormDTO signInFormDTO) {
    	Member member = memberRepository.findByUsername(signInFormDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        
        if (!passwordEncoder.matches(signInFormDTO.getPassword(), member.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String accessToken = jwtUtil.generateAccessToken(signInFormDTO.getUsername());
//        String refreshToken = jwtUtil.generateRefreshToken(signInFormDTO.getUsername());
        
        TokenResponse tokenResponse = TokenResponse.builder()
        									.accessToken(accessToken)
//        								    .refreshToken(refreshToken)
        								    .build();
        return tokenResponse;
    }
}