package com.ssafy.solvedpick.accounts.service;

import com.ssafy.solvedpick.accounts.dto.SignupFormDTO;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.members.repository.MemberRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Member create(SignupFormDTO signupFormDTO) {
    	Member user = Member.builder()
        		.username(signupFormDTO.getUsername())
        		.password(passwordEncoder.encode(signupFormDTO.getPassword()))
        		.build();
        
    	if(memberRepository.existsByUsername(signupFormDTO.getUsername())){
    		return user;
    	}

    	this.memberRepository.save(user);
    	return user;
	}
}
