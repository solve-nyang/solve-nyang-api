package com.ssafy.solvedpick.auth.presentation;

import com.ssafy.solvedpick.auth.dto.*;
import com.ssafy.solvedpick.auth.service.AuthService;
import com.ssafy.solvedpick.common.dto.ResponseMessageDTO;
import com.ssafy.solvedpick.global.error.dto.ErrorResponse;
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


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupFormDTO signupFormDTO) {
    	boolean verified = authService.verifyUser(signupFormDTO);

    	if (verified) {
    		authService.create(signupFormDTO);
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
        TokenResponse tokenResponse = authService.signIn(signInFormDTO);

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
    
}
