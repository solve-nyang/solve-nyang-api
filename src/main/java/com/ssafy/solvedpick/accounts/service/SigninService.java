package com.ssafy.solvedpick.accounts.service;

import com.ssafy.solvedpick.accounts.dto.SignInFormDTO;
import com.ssafy.solvedpick.jwt.JwtUtil;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.members.repository.MemberRepository;
import com.ssafy.solvedpick.accounts.dto.TokenResponse;
import com.ssafy.solvedpick.global.error.exception.UserInfoErrorException;

import lombok.RequiredArgsConstructor;

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
                .orElseThrow(() -> new UserInfoErrorException("Username Error"));
        
        if (!passwordEncoder.matches(signInFormDTO.getPassword(), member.getPassword())) {
            throw new UserInfoErrorException("Password Error");
        }

        String accessToken = jwtUtil.generateAccessToken(signInFormDTO.getUsername());
//        String refreshToken = jwtUtil.generateRefreshToken(signInFormDTO.getUsername());
        
        TokenResponse tokenResponse = TokenResponse.builder()
        				.accessToken(accessToken)
//        				.refreshToken(refreshToken)
        				.build();
        return tokenResponse;
    }
}