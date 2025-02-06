package com.ssafy.solvedpick.composition.renderer;

import com.ssafy.solvedpick.common.enums.AnimationType;
import com.ssafy.solvedpick.common.enums.AvatarType;
import com.ssafy.solvedpick.common.enums.BackgroundType;
import com.ssafy.solvedpick.composition.renderer.types.Path;
import com.ssafy.solvedpick.composition.renderer.types.Position;
import com.ssafy.solvedpick.composition.resource.SvgResources;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AvatarRenderer {
    private static final int SVG_WIDTH = 600;
    private static final int SVG_HEIGHT = 300;
    private static final int MARGIN = 70;
    private static final double AVATAR_SCALE = 0.8;
    private static final int BASE_AVATAR_SIZE = 100;
    private static final int SCALED_AVATAR_SIZE = (int)(BASE_AVATAR_SIZE * AVATAR_SCALE);

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
            int startX = random.nextInt(SVG_WIDTH - SCALED_AVATAR_SIZE);
            int startY = random.nextInt(SVG_HEIGHT - SCALED_AVATAR_SIZE);

            appendAvatar(content, avatars.get(i), startX, startY, i);
        }
    }

    private void appendAvatar(StringBuilder content, AvatarType avatar, int startX, int startY, int index) {
        Position edgePosition = calculateEdgePosition();
        List<Position> controlPositions = calculateControlPositions();
        Path path = calculatePath(startX, startY, edgePosition, controlPositions);
        appendSvgContent(content, avatar, startX, startY, path);
    }

    private Position calculateEdgePosition() {
        SecureRandom random = new SecureRandom();
        int minX = MARGIN;
        int maxX = SVG_WIDTH - MARGIN;
        int minY = MARGIN;
        int maxY = SVG_HEIGHT - MARGIN;

        int edge = random.nextInt(4);
        int x, y;

        switch (edge) {
            case 0:
                x = minX + random.nextInt(maxX - minX + 1);
                y = minY + random.nextInt(MARGIN);
                break;
            case 1:
                x = maxX - random.nextInt(MARGIN);
                y = minY + random.nextInt(maxY - minY + 1);
                break;
            case 2:
                x = minX + random.nextInt(maxX - minX + 1);
                y = maxY - random.nextInt(MARGIN);
                break;
            default:
                x = minX + random.nextInt(MARGIN);
                y = minY + random.nextInt(maxY - minY + 1);
                break;
        }
        return new Position(x, y);
    }

    private List<Position> calculateControlPositions() {
        SecureRandom random = new SecureRandom();
        int minX = MARGIN;
        int maxX = SVG_WIDTH - MARGIN;
        int minY = MARGIN;
        int maxY = SVG_HEIGHT - MARGIN;

        List<Position> positions = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            int x = minX + random.nextInt(maxX - minX + 1);
            int y = minY + random.nextInt(maxY - minY + 1);
            positions.add(new Position(x, y));
        }
        return positions;
    }

    private Path calculatePath(int startX, int startY, Position edge, List<Position> controls) {
        return new Path(
                controls.get(0).x() - startX,
                controls.get(0).y() - startY,
                controls.get(1).x() - startX,
                controls.get(1).y() - startY,
                edge.x() - startX,
                edge.y() - startY
        );
    }

    private void appendSvgContent(StringBuilder content, AvatarType avatar, int startX, int startY, Path path) {
        SecureRandom random = new SecureRandom();

        content.append(String.format("<g transform=\"translate(%d, %d) scale(%.1f)\">", startX, startY, AVATAR_SCALE));
        content.append(avatar.getSvgContent(svgResources));
        content.append(AnimationType.FLOAT.format(
                0, 0,
                path.midX1(), path.midY1(),
                path.midX2(), path.midY2(),
                path.endX(), path.endY(),
                0, 0,
//                15));
                random.nextInt(50) + 10));

        content.append("</g>");
    }

    private StringBuilder openFile() {
        return new StringBuilder().append("<svg width=\"600\" height=\"300\" viewBox=\"0 0 600 300\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">");
    }

    private StringBuilder closeFile(StringBuilder file) {
        return file.append("</svg>");
    }
}