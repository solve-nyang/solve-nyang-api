package com.ssafy.solvedpick.composition.renderer;

import com.ssafy.solvedpick.common.enums.AnimationType;
import com.ssafy.solvedpick.common.enums.AvatarType;
import com.ssafy.solvedpick.common.enums.BackgroundType;
import com.ssafy.solvedpick.composition.resource.SvgResources;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AvatarRenderer {
    private static final int SVG_WIDTH = 600;
    private static final int SVG_HEIGHT = 300;
    private final SvgResources svgResources;

    public String renderAvatars(BackgroundType background, List<AvatarType> avatars) {

        StringBuilder content = openFile();
        content.append(background.getSvgContent(svgResources));
        placeAvatars(content, avatars);
        return closeFile(content).toString();
    }

    private void placeAvatars(StringBuilder content, List<AvatarType> avatars) {
        for (int i = 0; i < avatars.size(); i++) {
            SecureRandom random = new SecureRandom();
            int scaledWidth = (int)(avatars.get(i).getWidth() * 0.3);
            int scaledHeight = (int)(avatars.get(i).getHeight() * 0.3);
            int startX = random.nextInt(SVG_WIDTH - scaledWidth);
            int startY = random.nextInt(SVG_HEIGHT - scaledHeight);

            appendAvatar(content, avatars.get(i), startX, startY, i);
        }
    }

    private void appendAvatar(StringBuilder content, AvatarType avatar, int startX, int startY, int index) {
        SecureRandom random = new SecureRandom();
        int scaledWidth = (int)(avatar.getWidth() * 0.5);
        int scaledHeight = (int)(avatar.getHeight() * 0.5);

        startX = Math.min(SVG_WIDTH - scaledWidth, Math.max(0, startX));
        startY = Math.min(SVG_HEIGHT - scaledHeight, Math.max(0, startY));

        int moveRange = 400;

        int endX = startX + (random.nextInt(moveRange * 2) - moveRange);
        int endY = startY + (random.nextInt(moveRange * 2) - moveRange);

        endX = Math.min(SVG_WIDTH - scaledWidth, Math.max(0, endX));
        endY = Math.min(SVG_HEIGHT - scaledHeight, Math.max(0, endY));

        int midX1 = Math.min(SVG_WIDTH - scaledWidth, Math.max(0, startX + (endX - startX)/3));
        int midY1 = Math.min(SVG_HEIGHT - scaledHeight, Math.max(0, startY + (endY - startY)/3));

        int midX2 = Math.min(SVG_WIDTH - scaledWidth, Math.max(0, startX + (endX - startX)*2/3));
        int midY2 = Math.min(SVG_HEIGHT - scaledHeight, Math.max(0, startY + (endY - startY)*2/3));

        content.append(String.format("<g transform=\"translate(%d, %d) scale(0.3)\">", startX, startY));
        content.append(avatar.getSvgContent(svgResources));
        content.append(avatar.getAnimationType().format(
                0, 0,
                midX1 - startX, midY1 - startY,
                midX2 - startX, midY2 - startY,
                endX - startX, endY - startY,
                0, 0,
                random.nextInt( 20)+10));
        content.append("</g>");
    }

    private StringBuilder openFile() {
        return new StringBuilder().append("<svg width=\"600\" height=\"300\" viewBox=\"0 0 600 300\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">");
    }

    private StringBuilder closeFile(StringBuilder file) {
        return file.append("</svg>");
    }
}