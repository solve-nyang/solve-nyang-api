package com.ssafy.solvedpick.composition.animation;

import com.ssafy.solvedpick.common.enums.AvatarType;
import com.ssafy.solvedpick.common.enums.BackgroundType;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

public class AnimationCalculator {
    private static final SecureRandom random = new SecureRandom();
    private static final float SCALE = 0.3f;
    private static final int SVG_WIDTH = 600;
    private static final int SVG_HEIGHT = 300;

    public static AnimationParams calculateParams(int startX, int startY, AvatarType avatar, BackgroundType background) {

        int scaledSize = (int)(Math.max(avatar.getWidth(), avatar.getHeight()) * SCALE);


        int moveRange = switch (background) {
            case STAR_FIELD -> 120;
            case SPACE_FIELD -> 100;
            case BASE_FIELD -> 80;
        };

        startX = Math.min(SVG_WIDTH - scaledSize, Math.max(scaledSize, startX));
        startY = Math.min(SVG_HEIGHT - scaledSize, Math.max(scaledSize, startY));

        int x1 = limitPosition(random.nextInt(SVG_WIDTH), scaledSize);
        int y1 = limitPosition(random.nextInt(SVG_HEIGHT), scaledSize);
        int x2 = limitPosition(random.nextInt(SVG_WIDTH), scaledSize);
        int y2 = limitPosition(random.nextInt(SVG_HEIGHT), scaledSize);

        int[][] positions = new int[][]{
                {startX, startY},
                {x1, y1},
                {x2, y2},
                {x2, y2},
                {x1, y1},
                {startX, startY}
        };

        return AnimationParams.builder()
                .positions(positions)
                .duration(random.nextInt(10) + 10)
                .build();
    }

    private static int limitPosition(int pos, int size) {
        return Math.min(SVG_WIDTH - size, Math.max(size, pos));
    }
}