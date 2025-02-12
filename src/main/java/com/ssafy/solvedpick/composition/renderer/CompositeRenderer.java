package com.ssafy.solvedpick.composition.renderer;

import com.ssafy.solvedpick.common.enums.AvatarType;
import com.ssafy.solvedpick.common.enums.BackgroundType;
import com.ssafy.solvedpick.common.utils.point.Tier;
import com.ssafy.solvedpick.composition.renderer.types.SvgDimensions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompositeRenderer {
    private static final int SVG_WIDTH = 600;
    private static final int SVG_HEIGHT = 300;
    private static final int MARGIN = 70;
    private static final double AVATAR_SCALE = 0.8;
    private static final int BASE_AVATAR_SIZE = 100;
    private static final int SCALED_AVATAR_SIZE = (int)(BASE_AVATAR_SIZE * AVATAR_SCALE);

    private static final int TITLE_START_X = 45;
    private static final int TITLE_START_Y = 20;
    private static final int INFO_END_X = 580;
    private static final int INFO_GAP = 10;


    private final BackgroundRenderer backgroundRenderer;
    private final AvatarRenderer avatarRenderer;
    private final LetterRenderer letterRenderer;
    private final InfoRenderer infoRenderer;
    private final TierRenderer tierRenderer;

    public String render(BackgroundType background, List<AvatarType> avatars, String title, Tier tier, Integer classNum, Integer solvedCount, Integer streakCount) {
        StringBuilder content = openFile();
        backgroundRenderer.renderBackground(content, background);
        avatarRenderer.renderAvatars(content, avatars, getSvgDimensions());
        letterRenderer.renderTitle(content, title, TITLE_START_X, TITLE_START_Y);
        infoRenderer.renderInfo(content, classNum, solvedCount, streakCount, INFO_END_X, INFO_GAP);
        tierRenderer.renderTier(content, tier);
        return closeFile(content).toString();
    }

    private StringBuilder openFile() {
        return new StringBuilder().append(
                String.format("<svg width=\"%d\" height=\"%d\" viewBox=\"0 0 %d %d\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">",
                        SVG_WIDTH, SVG_HEIGHT, SVG_WIDTH, SVG_HEIGHT));
    }

    private StringBuilder closeFile(StringBuilder file) {
        return file.append("</svg>");
    }

    private SvgDimensions getSvgDimensions() {
        return new SvgDimensions(SVG_WIDTH, SVG_HEIGHT, MARGIN, AVATAR_SCALE, SCALED_AVATAR_SIZE);
    }
}