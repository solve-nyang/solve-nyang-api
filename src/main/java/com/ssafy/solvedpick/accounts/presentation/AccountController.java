package com.ssafy.solvedpick.accounts.presentation;

import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.Map;

import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.solvedpick.accounts.dto.SignupFormDTO;
import com.ssafy.solvedpick.accounts.dto.SignInFormDTO;
import com.ssafy.solvedpick.accounts.dto.TokenResponse;
import com.ssafy.solvedpick.accounts.dto.MembernameDTO;
import com.ssafy.solvedpick.accounts.service.MemberService;
import com.ssafy.solvedpick.accounts.service.SigninService;
import com.ssafy.solvedpick.accounts.service.VerificationService;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final MemberService memberService;
    private final SigninService signinService;
    private final VerificationService verificationService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(SignupFormDTO signupFormDTO) {
    	boolean verified = verificationService.verifyUser(signupFormDTO);
    	if (verified) {
    		memberService.create(signupFormDTO);
    		return ResponseEntity.ok().body("success signup");    		
    	}
    	return ResponseEntity.ok().body("failed to signup");
    }
    
    
    @PostMapping("/signin")
    public ResponseEntity<?> signin(SignInFormDTO signInFormDTO) {
        TokenResponse tokenResponse = signinService.signIn(signInFormDTO);

        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", tokenResponse.getAccessToken())
        	       .httpOnly(true)
        	       .secure(true)  // HTTPS 사용시.
        	       .sameSite("None") // 추후 포트 맞출 필요 있음
        	       .maxAge(Duration.ofMinutes(30))
        	       .path("/")
        	       .build();
        	       
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", tokenResponse.getRefreshToken())
        	       .httpOnly(true)
        	       .secure(true)  // HTTPS 사용시.
        	       .sameSite("None")  // 추후 포트 맞출 필요 있음
        	       .maxAge(Duration.ofMinutes(120))
        	       .path("/")
        	       .build();
        
        return ResponseEntity.ok()
            .body(Map.of(
                "accessToken", accessTokenCookie,
                "refreshToken", refreshTokenCookie
            ));
    }

    @GetMapping("/verify")
    public ResponseEntity<?> getVerificationCode(MembernameDTO membernameDTO) {
    	String username = membernameDTO.getUsername();
    	boolean check = verificationService.checkUser(username);
    	if (check) {
    		String code = verificationService.generateVerificationCode(username);
    		return ResponseEntity.ok().body(Map.of("verificationCode", code));
    	} else return ResponseEntity.ok().body(Map.of("error", "incorrect username info"));
    }
    
}
