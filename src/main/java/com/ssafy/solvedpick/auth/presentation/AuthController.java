package com.ssafy.solvedpick.auth.presentation;

import com.ssafy.solvedpick.auth.dto.*;
import com.ssafy.solvedpick.auth.service.AuthService;
import com.ssafy.solvedpick.common.dto.ResponseMessageDTO;
import com.ssafy.solvedpick.common.error.dto.ErrorResponse;
import com.ssafy.solvedpick.members.repository.MemberRepository;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
        authService.isValidPassword(userDataDTO.getPassword());
        if(memberRepository.existsByUsername(userDataDTO.getUsername())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessageDTO.builder()
                .message("이미 가입된 회원입니다.")
                .build());
        }
        boolean verified = authService.verifyUser(userDataDTO);
                
    	if (verified) {
    		authService.create(userDataDTO);
    		return ResponseEntity.ok()
                    .body(ResponseMessageDTO.builder()
                            .message("success")
                            .build());
    	}
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessageDTO.builder()
                        .message("solved.ac에 암호화 키를 잘 저장하였는지 확인하세요.")
                        .build());
    }
    
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody UserDataDTO userDataDTO, HttpServletResponse response) {
        TokenResponse tokenResponse = authService.signIn(userDataDTO, response);
        
        return ResponseEntity.ok()
            .body(Map.of("accessToken", tokenResponse.getAccessToken()));
    }
    
    @GetMapping("/signout")
    public ResponseEntity<?> signout(HttpServletResponse response) {
    	ResponseCookie cookie = ResponseCookie.from("refresh_token", "")
    	        .httpOnly(true)
    	        .secure(true)
    	        .path("/")
    	        // .domain("www.solve-nyang.com")
    	        .maxAge(0)
    	        .sameSite("None")
    	        .build();
    	response.addHeader("Set-Cookie", cookie.toString());
    	
    	return ResponseEntity.ok()
    	        .body(ResponseMessageDTO.builder()
                        .message("로그아웃 되었습니다.")
                        .build());
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
                        .message("존재하지 않는 유저입니다.")
                        .build());
    }
    
    @PostMapping("/password/change")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        authService.isValidPassword(changePasswordDTO.getNewPassword());
        authService.changePassword(changePasswordDTO);

        return ResponseEntity.ok()
                .body(ResponseMessageDTO.builder()
                .message("success")
                .build());
    }

    @PostMapping("password/find")
    public ResponseEntity<?> findPassword(@RequestBody UserDataDTO userDataDTO) {
        authService.isValidPassword(userDataDTO.getPassword());
        authService.findPassword(userDataDTO);

        return ResponseEntity.ok()
                .body(ResponseMessageDTO.builder()
                .message("success")
                .build());
    }
}
