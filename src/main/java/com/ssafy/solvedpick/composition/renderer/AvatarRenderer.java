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
            int scaledWidth = 70;
            int scaledHeight = 70;
            int startX = random.nextInt(SVG_WIDTH - scaledWidth);
            int startY = random.nextInt(SVG_HEIGHT - scaledHeight);

            appendAvatar(content, avatars.get(i), startX, startY, i);
        }
    }

    private void appendAvatar(StringBuilder content, AvatarType avatar, int startX, int startY, int index) {
        SecureRandom random = new SecureRandom();

        // 화면 가장자리 여백
        int margin = 60;

        int minX = margin;  // 왼쪽 경계
        int maxX = SVG_WIDTH - margin;  // 오른쪽 경계
        int minY = margin;  // 위쪽 경계
        int maxY = SVG_HEIGHT - margin;  // 아래쪽 경계

        // 끝점을 화면 가장자리 근처에 배치
        int endX, endY;

        // 화면 가장자리 영역 중 하나를 랜덤하게 선택
        int edge = random.nextInt(4); // 0: 위, 1: 오른쪽, 2: 아래, 3: 왼쪽
        switch (edge) {
            case 0: // 위쪽 가장자리
                endX = minX + random.nextInt(maxX - minX + 1);
                endY = minY + random.nextInt(margin);
                break;
            case 1: // 오른쪽 가장자리
                endX = maxX - random.nextInt(margin);
                endY = minY + random.nextInt(maxY - minY + 1);
                break;
            case 2: // 아래쪽 가장자리
                endX = minX + random.nextInt(maxX - minX + 1);
                endY = maxY - random.nextInt(margin);
                break;
            default: // 왼쪽 가장자리
                endX = minX + random.nextInt(margin);
                endY = minY + random.nextInt(maxY - minY + 1);
                break;
        }

        // 중간 제어점들을 전체 범위에서 랜덤하게 배치
        int mid1X = minX + random.nextInt(maxX - minX + 1);
        int mid1Y = minY + random.nextInt(maxY - minY + 1);
        int mid2X = minX + random.nextInt(maxX - minX + 1);
        int mid2Y = minY + random.nextInt(maxY - minY + 1);

        // 상대 좌표 계산
        int deltaMidX1 = mid1X - startX;
        int deltaMidY1 = mid1Y - startY;
        int deltaMidX2 = mid2X - startX;
        int deltaMidY2 = mid2Y - startY;
        int deltaX = endX - startX;
        int deltaY = endY - startY;

        // 디버깅을 위한 로그
        log.debug("Avatar {}: Start=({},{}), Path points: Start=({},{}), Mid1=({},{}), Mid2=({},{}), End=({},{})",
                index, startX, startY,
                startX, startY,
                mid1X, mid1Y,
                mid2X, mid2Y,
                endX, endY);
        log.debug("Bounds: X=[{},{}], Y=[{},{}]", minX, maxX, minY, maxY);

        AnimationType[] animations = AnimationType.values();
        AnimationType selectedAnimation = animations[random.nextInt(animations.length)];

        content.append(String.format("<g transform=\"translate(%d, %d) scale(0.8)\">", startX, startY));
        content.append(avatar.getSvgContent(svgResources));
        content.append(selectedAnimation.format(
                0, 0,
                deltaMidX1, deltaMidY1,
                deltaMidX2, deltaMidY2,
                deltaX, deltaY,
                0, 0,
//                20));
                random.nextInt(20) + 20));
        content.append("</g>");
    }
    private StringBuilder openFile() {
        return new StringBuilder().append("<svg width=\"600\" height=\"300\" viewBox=\"0 0 600 300\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">");
    }

    private StringBuilder closeFile(StringBuilder file) {
        return file.append("</svg>");
    }
}