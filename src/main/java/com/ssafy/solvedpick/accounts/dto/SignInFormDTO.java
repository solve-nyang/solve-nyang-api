package com.ssafy.solvedpick.accounts.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInFormDTO {
    private String username;
    private String password;

}
