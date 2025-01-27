package com.ssafy.solvedpick.auth.presentation;

import com.ssafy.solvedpick.auth.dto.*;
import com.ssafy.solvedpick.auth.service.AuthService;
import com.ssafy.solvedpick.common.dto.ResponseMessageDTO;
import com.ssafy.solvedpick.common.error.dto.ErrorResponse;
import com.ssafy.solvedpick.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MemberRepository memberRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDataDTO userDataDTO) {
    	boolean verified = authService.verifyUser(userDataDTO);
        if(memberRepository.existsByUsername(userDataDTO.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessageDTO.builder()
                    .message("이미 가입된 회원입니다.")
                    .build());
        }

    	if (verified) {
    		authService.create(userDataDTO);
    		return ResponseEntity.ok()
                    .body(ResponseMessageDTO.builder()
                            .message("success")
                            .build());
    	}
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessageDTO.builder()
                        .message("solved.ac 인증을 확인하세요")
                        .build());
    }
    
    
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody UserDataDTO userDataDTO) {
        TokenResponse tokenResponse = authService.signIn(userDataDTO);

        return ResponseEntity.ok()
            .body(tokenResponse);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> getVerificationCode(@RequestBody MemberNameDTO membernameDTO) {
    	String username = membernameDTO.getUsername();
    	boolean check = authService.checkUser(username);

    	if (check) {
    		String code = authService.generateVerificationCode(username);
            VerificationResponseDTO result = new VerificationResponseDTO(code);
    		return ResponseEntity.ok()
                    .body(result);
    	}

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .message("Invalid user"));
    }
    
    @PostMapping("/password/change")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        authService.changePassword(changePasswordDTO);

        return ResponseEntity.ok()
                .body(ResponseMessageDTO.builder()
                .message("success")
                .build());
    }

    @PostMapping("password/find")
    public ResponseEntity<?> findPassword(@RequestBody UserDataDTO userDataDTO) {
        authService.findPassword(userDataDTO);

        return ResponseEntity.ok()
                .body(ResponseMessageDTO.builder()
                .message("success")
                .build());
    }
}
