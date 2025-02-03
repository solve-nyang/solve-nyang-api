package com.ssafy.solvedpick.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AnimationType {

    FLOAT {
        @Override
        public String format(int x0, int y0, int x1, int y1, int x2, int y2,
                             int x3, int y3, int returnX, int returnY, int duration) {
            return String.format(
                    "<animateMotion dur=\"%ds\" repeatCount=\"indefinite\" " +
                            "path=\"M %d,%d C %d,%d %d,%d %d,%d L %d,%d\"/>",
                    duration, x0, y0, x1, y1, x2, y2, x3, y3, returnX, returnY
            );
        }
    };

    public abstract String format(int x0, int y0, int x1, int y1, int x2, int y2,
                                  int x3, int y3, int returnX, int returnY, int duration);
}