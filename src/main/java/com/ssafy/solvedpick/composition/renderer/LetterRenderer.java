package com.ssafy.solvedpick.composition.renderer;

import com.ssafy.solvedpick.common.enums.LetterType;
import com.ssafy.solvedpick.composition.resource.SvgResources;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LetterRenderer {

    private final SvgResources svgResources;

    public void renderTitle(StringBuilder content, String title, int startX, int startY) {
        if(title == null){
            return;
        }

        String[] parts = title.replaceAll("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)", " ").split(" ");
        int currentX = startX;

        for (String part : parts) {
            if (part.matches("\\d+")) {
                renderNumber(content, part, currentX, startY);
            } else {
                renderText(content, part, currentX, startY);
            }
            currentX += calculateTotalGap(part);
        }
    }

    public void renderText(StringBuilder content, String text, int startX, int startY) {
        renderContent(content, text, startX, startY, true);
    }

    public void renderNumber(StringBuilder content, String numbers, int startX, int startY) {
        renderContent(content, numbers, startX, startY, false);
    }

    private void renderContent(StringBuilder content, String text, int startX, int startY, boolean isText) {
        int totalGap = 0;
        for(char c : text.toCharArray()) {
            LetterType type = LetterType.fromName(c);
            content.append(String.format("<g transform=\"translate(%d, %d) scale(1)\">",
                    startX + totalGap, startY + type.getHgap()));
            content.append(type.getSvgContent(svgResources));
            content.append("</g>");
            totalGap += type.getGap();
        }
    }

    private int calculateTotalGap(String text) {
        int totalGap = 0;
        for(char c : text.toCharArray()) {
            totalGap += LetterType.fromName(c).getGap();
        }
        return totalGap;
    }

    public void renderWithrenderWithRightAligned(StringBuilder content, String Type, int numbers, int endX, int startY, int fixedGap) {
        String numberStr = String.valueOf(numbers);
        int numberWidth = calculateTotalGap(numberStr);
        int streakWidth = calculateTotalGap(Type);
        
        int numberStartX = endX - numberWidth;
        int streakStartX = numberStartX - fixedGap - streakWidth;

        renderText(content, Type, streakStartX, startY);
        renderNumber(content, numberStr, numberStartX, startY);
    }
}
