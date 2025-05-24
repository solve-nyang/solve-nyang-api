package com.solvenyang.composition.renderer;

import com.solvenyang.common.enums.AnimationType;
import com.solvenyang.common.enums.AvatarType;
import com.solvenyang.composition.renderer.types.Path;
import com.solvenyang.composition.renderer.types.Position;
import com.solvenyang.composition.renderer.types.SvgDimensions;
import com.solvenyang.composition.resource.SvgResources;
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
    private final SvgResources svgResources;

    public void renderAvatars(StringBuilder content, List<AvatarType> avatars, SvgDimensions dimensions) {
        appendAvatarDefinitions(content, avatars);

        for (AvatarType avatar : avatars) {
            SecureRandom random = new SecureRandom();
            int startX = random.nextInt(dimensions.width() - dimensions.scaledAvatarSize());
            int startY = random.nextInt(dimensions.height() - dimensions.scaledAvatarSize());
            appendAvatar(content, avatar, startX, startY, dimensions);
        }
    }

    private void appendAvatar(StringBuilder content, AvatarType avatar, int startX, int startY, SvgDimensions dimensions) {
        Position edgePosition = calculateEdgePosition(dimensions);
        List<Position> controlPositions = calculateControlPositions(dimensions);
        Path path = calculatePath(startX, startY, edgePosition, controlPositions);
        appendSvgContent(content, avatar, startX, startY, path, dimensions.scale());
    }

    private Position calculateEdgePosition(SvgDimensions dimensions) {
        SecureRandom random = new SecureRandom();
        int minX = dimensions.margin();
        int maxX = dimensions.width() - dimensions.margin();
        int minY = dimensions.margin();
        int maxY = dimensions.height() - dimensions.margin();

        int edge = random.nextInt(4);
        int x, y;

        switch (edge) {
            case 0:
                x = minX + random.nextInt(maxX - minX + 1);
                y = minY + random.nextInt(dimensions.margin());
                break;
            case 1:
                x = maxX - random.nextInt(dimensions.margin());
                y = minY + random.nextInt(maxY - minY + 1);
                break;
            case 2:
                x = minX + random.nextInt(maxX - minX + 1);
                y = maxY - random.nextInt(dimensions.margin());
                break;
            default:
                x = minX + random.nextInt(dimensions.margin());
                y = minY + random.nextInt(maxY - minY + 1);
                break;
        }
        return new Position(x, y);
    }

    private List<Position> calculateControlPositions(SvgDimensions dimensions) {
        SecureRandom random = new SecureRandom();
        int minX = dimensions.margin();
        int maxX = dimensions.width() - dimensions.margin();
        int minY = dimensions.margin();
        int maxY = dimensions.height() - dimensions.margin();

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

    private void appendSvgContent(StringBuilder content, AvatarType avatar, int startX, int startY, Path path, double scale) {
        SecureRandom random = new SecureRandom();
        String avatarId = "avatar-" + avatar.getName();

        content.append(String.format("<g transform=\"translate(%d, %d) scale(%.1f)\">", startX, startY, scale));
        content.append("<use href=\"#").append(avatarId).append("\"/>");
        content.append(AnimationType.FLOAT.format(
                0, 0,
                path.midX1(), path.midY1(),
                path.midX2(), path.midY2(),
                path.endX(), path.endY(),
                0, 0,
                random.nextInt(50) + 10));
        content.append("</g>");
    }

    private void appendAvatarDefinitions(StringBuilder content, List<AvatarType> avatars) {
        content.append("<defs>");
        List<AvatarType> distinctAvatars = avatars.stream()
                .distinct()
                .toList();

        for (AvatarType distinctAvatar : distinctAvatars) {
            String avatarId = "avatar-" + distinctAvatar.getName();
            content.append("<g id=\"").append(avatarId).append("\">");
            content.append(distinctAvatar.getSvgContent(svgResources));
            content.append("</g>");
        }
        content.append("</defs>");
    }
}