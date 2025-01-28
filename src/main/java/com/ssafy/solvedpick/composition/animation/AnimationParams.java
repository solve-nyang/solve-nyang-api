package com.ssafy.solvedpick.composition.animation;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnimationParams {
    private final int[][] positions;
    private final int duration;
}
