package com.ssafy.solvedpick.composition.renderer;

import com.ssafy.solvedpick.common.enums.AnimationType;
import com.ssafy.solvedpick.common.enums.AvatarType;
import com.ssafy.solvedpick.common.enums.BackgroundType;
import com.ssafy.solvedpick.composition.resource.SvgResources;
import lombok.RequiredArgsConstructor;
import lombok.Value;
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
    private static final int AVATAR_SIZE = 100;
    private BackgroundType background;
    private final SvgResources svgResources;

    public String renderAvatars(BackgroundType background, List<AvatarType> avatars) {
        this.background= background;
        StringBuilder content = openFile();
        content.append(background.getSvgContent(svgResources));
        placeAvatars(content, avatars);
        return closeFile(content).toString();
    }

    private void placeAvatars(StringBuilder content, List<AvatarType> avatars) {

        for (int i = 0; i < avatars.size(); i++) {
            SecureRandom random = new SecureRandom();
            int startX = random.nextInt(SVG_WIDTH - AVATAR_SIZE);
            int startY = random.nextInt(SVG_HEIGHT - AVATAR_SIZE);

            appendAvatar(content, avatars.get(i), startX, startY, i);
        }
    }

    private void appendAvatar(StringBuilder content, AvatarType avatar, int startX, int startY, int index) {
        SecureRandom random = new SecureRandom();
        int scaledSize = (int)(AVATAR_SIZE * 0.3);

        startX = Math.min(SVG_WIDTH - scaledSize, Math.max(0, startX));
        startY = Math.min(SVG_HEIGHT - scaledSize, Math.max(0, startY));

        int moveRange = 50;
        int endX = startX + (random.nextInt(moveRange * 2) - moveRange);
        int endY = startY + (random.nextInt(moveRange * 2) - moveRange);

        endX = Math.min(SVG_WIDTH - scaledSize, Math.max(0, endX));
        endY = Math.min(SVG_HEIGHT - scaledSize, Math.max(0, endY));

        content.append(String.format("<g transform=\"translate(%d, %d) scale(0.3)\">", startX, startY));
        content.append(avatar.getSvgContent(svgResources));
        content.append(AnimationType.SPACE_FLOAT.format(
                0, 0,
                (endX - startX)/2, (endY - startY)/2,
                (endX - startX)/2, (endY - startY)/2,
                endX - startX, endY - startY,
                0, 0,
                random.nextInt(20) + 20));
        content.append("</g>");
    }

    private String getAnimation(int startX, int startY, int index) {
        return generateKeyframes(index);
    }

    private String generateKeyframes(int index) {
        StringBuilder keyframes = new StringBuilder();
        List<Point> points = new ArrayList<>();
        SecureRandom random = new SecureRandom();

        points = generatePoints(random);

        keyframes.append(".avatar-").append(index)
                .append(" { animation: move-").append(index)
                .append(" ").append(random.nextInt(10, 20)).append("s infinite; }")
                .append("@keyframes move-").append(index).append(" {");

        points.forEach(p -> appendKeyframe(keyframes, p));
        return keyframes.append("}").toString();
    }

    private void appendKeyframe(StringBuilder keyframes, Point point) {
        keyframes.append(String.format("%.1f%% { ", point.percentage))
                .append(String.format("transform: translate(%dpx, %dpx) ", point.x, point.y))
                .append(String.format("rotate(%.1fdeg); }", point.rotation));
    }

    private double calculateRotation(int fromX, int fromY, int toX, int toY) {
        return Math.toDegrees(Math.atan2(toY - fromY, toX - fromX));
    }

    private List<Point> generatePoints(SecureRandom random) {
        List<Point> points = new ArrayList<>();
        int currentX = random.nextInt(SVG_WIDTH - AVATAR_SIZE);
        int currentY = random.nextInt(SVG_HEIGHT - AVATAR_SIZE);
        double percentage = 0;

        while (percentage < 100) {
            percentage += random.nextInt(4) + 2;

            // 다음 위치 계산
            int nextX = Math.min(SVG_WIDTH - AVATAR_SIZE,
                    Math.max(0, currentX + random.nextInt(81) - 40));
            int nextY = Math.min(SVG_HEIGHT - AVATAR_SIZE,
                    Math.max(0, currentY + random.nextInt(81) - 40));

            points.add(new Point(
                    Math.min(100, percentage),
                    nextX,
                    nextY,
                    calculateRotation(currentX, currentY, nextX, nextY)
            ));

            currentX = nextX;
            currentY = nextY;
        }

        return points;
    }

    @Value
    private static class Point {
        double percentage;
        int x, y;
        double rotation;
    }

    private StringBuilder openFile() {
        return new StringBuilder().append("<svg width=\"600\" height=\"300\" viewBox=\"0 0 600 300\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">");
    }

    private StringBuilder closeFile(StringBuilder file) {
        return file.append("</svg>");
    }
}