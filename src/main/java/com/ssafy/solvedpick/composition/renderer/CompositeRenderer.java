package com.ssafy.solvedpick.composition.renderer;

import com.ssafy.solvedpick.common.enums.AvatarType;
import com.ssafy.solvedpick.common.enums.BackgroundType;
import com.ssafy.solvedpick.composition.renderer.types.SvgDimensions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CompositeRenderer {
    private static final int SVG_WIDTH = 600;
    private static final int SVG_HEIGHT = 300;
    private static final int MARGIN = 70;
    private static final double AVATAR_SCALE = 0.8;
    private static final int BASE_AVATAR_SIZE = 100;
    private static final int SCALED_AVATAR_SIZE = (int)(BASE_AVATAR_SIZE * AVATAR_SCALE);

    private final BackgroundRenderer backgroundRenderer;
    private final AvatarRenderer avatarRenderer;
    private final LetterRenderer letterRenderer;

    public String render(BackgroundType background, List<AvatarType> avatars, String username) {
        StringBuilder content = openFile();
        backgroundRenderer.renderBackground(content, background);
        letterRenderer.renderUsername(content, username, 40, 30);
        avatarRenderer.renderAvatars(content, avatars, getSvgDimensions());
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