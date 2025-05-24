package com.solvenyang.composition.renderer;

import com.solvenyang.common.utils.point.Tier;
import com.solvenyang.composition.resource.SvgResources;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TierRenderer {

    private final SvgResources svgResources;

    public void renderTier(StringBuilder content, Tier tier) {
        if(tier == null) {
            return;
        }
        content.append(String.format("<g transform=\"translate(%d, %d) scale(1)\">", 20, 20));
        content.append(tier.getSvgContent(svgResources));
        content.append("</g>");
    }
}
