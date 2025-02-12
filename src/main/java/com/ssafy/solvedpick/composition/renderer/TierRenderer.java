package com.ssafy.solvedpick.composition.renderer;

import com.ssafy.solvedpick.common.utils.point.Tier;
import com.ssafy.solvedpick.composition.resource.SvgResources;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TierRenderer {

    private SvgResources svgResources;

    public void renderTier(StringBuilder content, Tier tier) {
        content.append(String.format("<g transform=\"translate(%d, %d) scale(1)\">", 20, 20));
        content.append(tier.getSvgContent(svgResources));
        content.append("</g>");
    }
}
