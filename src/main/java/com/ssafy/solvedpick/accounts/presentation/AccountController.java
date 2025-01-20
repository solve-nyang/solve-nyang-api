package com.ssafy.solvedpick.accounts.presentation;

import com.ssafy.solvedpick.accounts.dto.*;
import com.ssafy.solvedpick.common.dto.ResponseMessageDTO;
import com.ssafy.solvedpick.global.error.dto.ErrorResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.solvedpick.accounts.service.SignupService;
import com.ssafy.solvedpick.accounts.service.SigninService;
import com.ssafy.solvedpick.accounts.service.VerificationService;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final SignupService signupService;
    private final SigninService signinService;
    private final VerificationService verificationService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupFormDTO signupFormDTO) {
    	boolean verified = verificationService.verifyUser(signupFormDTO);

    	if (verified) {
    		signupService.create(signupFormDTO);
    		return ResponseEntity.ok()
                    .body(ResponseMessageDTO.builder()
                            .message("success")
                            .build());
    	}
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessageDTO.builder()
                        .message("failed")
                        .build());
    }
    
    
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SignInFormDTO signInFormDTO) {
        TokenResponse tokenResponse = signinService.signIn(signInFormDTO);

        return ResponseEntity.ok()
            .body(tokenResponse);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> getVerificationCode(@RequestBody MemberNameDTO membernameDTO) {
    	String username = membernameDTO.getUsername();
    	boolean check = verificationService.checkUser(username);

    	if (check) {
    		String code = verificationService.generateVerificationCode(username);
            VerificationResponseDTO result = new VerificationResponseDTO(code);
    		return ResponseEntity.ok()
                    .body(result);
    	}

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .message("Invalid user"));
    }
    
}
