package com.ssafy.solvedpick.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AnimationType {

    FLOAT("""
        <animateTransform 
            attributeName="transform" 
            type="translate"
            values="%d,%d; %d,%d; %d,%d; %d,%d; %d,%d; %d,%d; %d,%d"
            calcMode="linear"
            dur="%ds" 
            repeatCount="indefinite" />
    """),;

    // 점프
//    JUMP(/* 점프하는 움직임의 keyframe values 설정 */),
//
//    // 회전하며 움직이기
//    ROTATE_MOVE(/* 회전하며 움직이는 애니메이션 설정 */);

    private final String animation;

    public String format(Object... args) {
        return String.format(animation, args);
    }
}
