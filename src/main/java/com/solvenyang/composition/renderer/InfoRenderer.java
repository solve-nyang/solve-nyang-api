package com.solvenyang.composition.renderer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InfoRenderer {

    private final LetterRenderer letterRenderer;

    private static final int CLASS_START_Y = 200;
    private static final int SOLVED_START_Y = 230;
    private static final int STREAK_START_Y = 260;

    public void renderInfo(StringBuilder content, Integer classNum, Integer solvedCount, Integer streakCount, int endX, int gap) {
        if (classNum != null) {
            letterRenderer.renderWithrenderWithRightAligned(content, "Class", classNum, endX, CLASS_START_Y, gap);
        }
        if (solvedCount != null) {
            letterRenderer.renderWithrenderWithRightAligned(content, "Solved", solvedCount, endX, SOLVED_START_Y, gap);
        }
        if (streakCount != null) {
            letterRenderer.renderWithrenderWithRightAligned(content, "Streak", streakCount, endX, STREAK_START_Y, gap);
        }
    }
}
