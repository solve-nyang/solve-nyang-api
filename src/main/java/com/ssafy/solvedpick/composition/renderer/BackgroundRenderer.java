package com.ssafy.solvedpick.composition.renderer;

import com.ssafy.solvedpick.common.enums.BackgroundType;
import com.ssafy.solvedpick.composition.resource.SvgResources;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BackgroundRenderer {

    private final SvgResources svgResources;

    public void renderBackground(StringBuilder content, BackgroundType background) {
        content.append(background.getSvgContent(svgResources));
    }
}
