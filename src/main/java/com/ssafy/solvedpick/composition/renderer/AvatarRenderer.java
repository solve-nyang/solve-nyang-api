package com.ssafy.solvedpick.composition.renderer;

import com.ssafy.solvedpick.common.enums.AnimationType;
import com.ssafy.solvedpick.common.enums.AvatarType;
import com.ssafy.solvedpick.common.enums.BackgroundType;
import com.ssafy.solvedpick.composition.animation.AnimationCalculator;
import com.ssafy.solvedpick.composition.animation.AnimationParams;
import com.ssafy.solvedpick.composition.resource.SvgResources;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        placeAvatars(content, avatars, background);
        return closeFile(content).toString();
    }

    private void placeAvatars(StringBuilder content, List<AvatarType> avatars, BackgroundType background) {

        for (int i = 0; i < avatars.size(); i++) {
            SecureRandom random = new SecureRandom();
            int startX = random.nextInt(SVG_WIDTH - AVATAR_SIZE);
            int startY = random.nextInt(SVG_HEIGHT - AVATAR_SIZE);

            appendAvatar(content, avatars.get(i), startX, startY, i, background);
        }
    }

    private void appendAvatar(StringBuilder content, AvatarType avatar, int startX, int startY, int index, BackgroundType background) {

        AnimationParams params = AnimationCalculator.calculateParams(startX, startY, avatar, background);


        content.append(String.format("<g transform=\"translate(%d, %d) scale(0.3)\">",
                params.getPositions()[0][0],
                params.getPositions()[0][1]));


        content.append(avatar.getSvgContent(svgResources));


        String positions = Arrays.stream(params.getPositions())
                .map(pos -> pos[0] + "," + pos[1])
                .collect(Collectors.joining("; "));

        content.append(avatar.getAnimationType().format(
                positions,
                params.getDuration()
        ));

        content.append("</g>");
    }

    private StringBuilder openFile() {
        return new StringBuilder().append("<svg width=\"600\" height=\"300\" viewBox=\"0 0 600 300\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">");
    }

    private StringBuilder closeFile(StringBuilder file) {
        return file.append("</svg>");
    }

}