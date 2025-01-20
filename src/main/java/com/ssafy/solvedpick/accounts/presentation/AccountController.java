package com.ssafy.solvedpick.accounts.presentation;

import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.Map;

import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.solvedpick.accounts.dto.SignupFormDTO;
import com.ssafy.solvedpick.accounts.dto.SignInFormDTO;
import com.ssafy.solvedpick.accounts.dto.TokenResponse;
import com.ssafy.solvedpick.accounts.dto.MembernameDTO;
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
    public ResponseEntity<?> signup(SignupFormDTO signupFormDTO) {
    	boolean verified = verificationService.verifyUser(signupFormDTO);
    	if (verified) {
    		signupService.create(signupFormDTO);
    		return ResponseEntity.ok().body(Map.of("message", "success"));
    	}
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "failed"));
    }
    
    	
    @PostMapping("/signin")
    public ResponseEntity<?> signin(SignInFormDTO signInFormDTO) {
        TokenResponse tokenResponse = signinService.signIn(signInFormDTO);
        final String accessToken = tokenResponse.getAccessToken();
//        final String refreshToken = tokenResponse.getRefreshToken();
        
//        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", accessToken)
//        	       .httpOnly(true)
//        	       .secure(true)  // HTTPS 사용시.
//        	       .sameSite("None") // 추후 포트 맞출 필요 있음
//        	       .maxAge(Duration.ofDays(1))
//        	       .path("/")
//        	       .build();
        	       
//        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken)
//        	       .httpOnly(true)
//        	       .secure(true)  // HTTPS 사용시.
//        	       .sameSite("None")  // 추후 포트 맞출 필요 있음
//        	       .maxAge(Duration.ofDays(1))
//        	       .path("/")
//        	       .build();
        
        return ResponseEntity.ok()
            .body(Map.of(
                "accessToken", accessToken
//                "refreshToken", refreshToken
            ));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> getVerificationCode(MembernameDTO membernameDTO) {
    	String username = membernameDTO.getUsername();
    	boolean check = verificationService.checkUser(username);
    	if (check) {
    		String code = verificationService.generateVerificationCode(username);
    		return ResponseEntity.ok().body(Map.of("verificationCode", code));
    	} else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "incorrect username info"));
    }
    
}
