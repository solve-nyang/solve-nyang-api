package com.ssafy.solvedpick.composition.renderer;

import com.ssafy.solvedpick.common.enums.AvatarType;
import com.ssafy.solvedpick.common.enums.BackgroundType;
import com.ssafy.solvedpick.composition.resource.SvgResources;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AvatarRenderer {
    private static final int SVG_WIDTH = 600;
    private static final int SVG_HEIGHT = 300;
    private static final int AVATAR_SIZE = 100;

    private final SvgResources svgResources;

    public String renderAvatars(BackgroundType background, List<AvatarType> avatars) {
        StringBuilder content = openFile();
        content.append(background.getSvgContent(svgResources));
        placeAvatars(content, avatars);
        return closeFile(content).toString();
    }

    private void placeAvatars(StringBuilder content, List<AvatarType> avatars) {
        List<Point> positions = generatePositions();
        Collections.shuffle(positions);

        for (int i = 0; i < avatars.size(); i++) {
            appendAvatar(content, avatars.get(i), positions.get(i));
        }
    }

    private List<Point> generatePositions() {
        List<Point> positions = new ArrayList<>();
        int cols = SVG_WIDTH / AVATAR_SIZE;
        int rows = SVG_HEIGHT / AVATAR_SIZE;

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                positions.add(new Point(i * AVATAR_SIZE, j * AVATAR_SIZE));
            }
        }
        return positions;
    }

    private void appendAvatar(StringBuilder content, AvatarType avatar, Point position) {
        log.debug("avatar = {}", avatar);
        content.append(String.format(
                "<g transform=\"translate(%d, %d) scale(0.8)\">",
                position.x, position.y
        ));
        content.append(avatar.getSvgContent(svgResources));
        content.append("</g>");
    }

    @Value
    private static class Point {
        int x;
        int y;
    }

    private StringBuilder openFile() {
        return new StringBuilder().append("<svg width=\"600\" height=\"300\" viewBox=\"0 0 600 300\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">");
    }

    private StringBuilder closeFile(StringBuilder file) {
        return file.append("</svg>");
    }
}