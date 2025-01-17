package com.ssafy.solvedpick.avatars.dto;


import lombok.Getter;

import java.util.List;

@Getter
public class AvatarResponse {
    // 리턴해주는 ]son 형식을 맞춰주기 위해 List<AvatarDto> 선언
    private final List<AvatarDto> avatars;


    public AvatarResponse(List<AvatarDto> avatars) {
        this.avatars = avatars;
    }
    //response 생성
    public static AvatarResponse of(List<AvatarDto> avatars) {
        return new AvatarResponse(avatars);
    }
}
