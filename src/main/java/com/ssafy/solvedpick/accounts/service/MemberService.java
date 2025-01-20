package com.ssafy.solvedpick.accounts.service;

import com.ssafy.solvedpick.accounts.domain.Member;
import com.ssafy.solvedpick.accounts.dto.SignupFormDTO;
import com.ssafy.solvedpick.accounts.dto.UserInfoResponse;
import com.ssafy.solvedpick.accounts.repository.MemberRepository;
import com.ssafy.solvedpick.api.dto.UserData;
import com.ssafy.solvedpick.api.dto.UserInfoApiResponse;
import com.ssafy.solvedpick.api.service.ApiService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ApiService apiService;

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
    
	//  더미아이디이름
	@Value("${API.ID}")
	private String membername;
	
	public UserInfoResponse getUserInfo(Long memberId) {
	//  String membername = memberRepository.findById(memberId)
	//          .orElseThrow(EntityNotFoundException::new)
	//          .getUsername();
	
	  UserInfoApiResponse apiResponse = apiService.getUserInfo(membername);
	  UserData userData = apiResponse.getItems().get(0);
	
	  return UserInfoResponse.builder()
	          .nickname(membername)
	//          더미 데이터
	          .point(123456)
	          .solvedacTier(userData.getTier())
	          .solvedCount(userData.getSolvedCount())
	          .solvedacStrick(userData.getMaxStreak())
	          .build();
}
}
