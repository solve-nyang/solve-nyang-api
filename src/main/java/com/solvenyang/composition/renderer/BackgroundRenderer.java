package com.solvenyang.composition.renderer;

import com.solvenyang.common.enums.BackgroundType;
import com.solvenyang.composition.resource.SvgResources;
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
