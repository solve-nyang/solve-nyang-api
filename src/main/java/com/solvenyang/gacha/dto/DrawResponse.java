package com.solvenyang.gacha.dto;

import com.solvenyang.avatars.dto.DrawAvatarDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class DrawResponse {
    private List<DrawAvatarDto> avatars;
}
