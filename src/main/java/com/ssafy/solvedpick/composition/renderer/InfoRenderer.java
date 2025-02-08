package com.ssafy.solvedpick.composition.renderer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InfoRenderer {

    private final LetterRenderer letterRenderer;

    private static final int CLASS_START_Y = 200;
    private static final int SOLVED_START_Y = 230;
    private static final int STREAK_START_Y = 260;

    public void renderInfo(StringBuilder content, int classNum, int solvedCount, int streakCount, int endX, int gap) {
        letterRenderer.renderWithrenderWithRightAligned(content, "Class", classNum, endX, CLASS_START_Y, gap);
        letterRenderer.renderWithrenderWithRightAligned(content, "Solved", solvedCount, endX, SOLVED_START_Y, gap);
        letterRenderer.renderWithrenderWithRightAligned(content, "Streak", streakCount, endX, STREAK_START_Y, gap);
    }
}
