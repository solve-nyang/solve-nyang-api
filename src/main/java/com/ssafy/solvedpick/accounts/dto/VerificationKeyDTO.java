package com.ssafy.solvedpick.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationKeyDTO {
    private String username;
    private String verificationCode;

}
