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
    },

    BOUNCE {
        @Override
        public String format(int x0, int y0, int x1, int y1, int x2, int y2,
                             int x3, int y3, int returnX, int returnY, int duration) {
            // y1, y2 값을 사용해서 bounceHeight 결정
            int bounceHeight = Math.min(30, Math.min(Math.abs(y1 - y0), Math.abs(y2 - y0)));
            return String.format(
                    "<animateMotion dur=\"%ds\" repeatCount=\"indefinite\" " +
                            "path=\"M %d,%d Q %d,%d %d,%d T %d,%d\"/>",
                    duration,
                    x0, y0,
                    (x0 + x3)/2, y0 - bounceHeight,
                    x3, y3,
                    x0, y0
            );
        }
    },

//    SPIN {
//        @Override
//        public String format(int x0, int y0, int x1, int y1, int x2, int y2,
//                             int x3, int y3, int returnX, int returnY, int duration) {
//            // 제어점들과의 거리를 이용해 radius 계산
//            int radius = Math.min(15, Math.min(
//                    Math.min(Math.abs(x1 - x0), Math.abs(x2 - x0)),
//                    Math.min(Math.abs(y1 - y0), Math.abs(y2 - y0))
//            ));
//
//            return String.format(
//                    "<animateMotion dur=\"%ds\" repeatCount=\"indefinite\" " +
//                            "path=\"M %d,%d A %d,%d 0 1 1 %d,%d A %d,%d 0 1 0 %d,%d\"/>",
//                    duration,
//                    radius, 0,
//                    radius, radius,
//                    -radius, 0,
//                    radius, radius,
//                    radius, 0
//            );
//        }
//    },

    SHAKE {
        @Override
        public String format(int x0, int y0, int x1, int y1, int x2, int y2,
                             int x3, int y3, int returnX, int returnY, int duration) {
            // 제어점들과의 거리를 이용해 shake 크기 결정
            int shakeAmount = Math.min(20, Math.min(Math.abs(x1 - x0), Math.abs(x2 - x0)));
            return String.format(
                    "<animateMotion dur=\"%ds\" repeatCount=\"indefinite\" " +
                            "path=\"M %d,%d L %d,%d L %d,%d L %d,%d\"/>",
                    duration,
                    0, 0,
                    -shakeAmount, 0,
                    shakeAmount, 0,
                    0, 0
            );
        }
    },

    ZIGZAG {
        @Override
        public String format(int x0, int y0, int x1, int y1, int x2, int y2,
                             int x3, int y3, int returnX, int returnY, int duration) {
            return String.format(
                    "<animateMotion dur=\"%ds\" repeatCount=\"indefinite\" " +
                            "path=\"M %d,%d L %d,%d L %d,%d L %d,%d Z\"/>",
                    duration,
                    x0, y0,
                    x1, y1,
                    x2, y2,
                    x3, y3
            );
        }
    };

    public abstract String format(int x0, int y0, int x1, int y1, int x2, int y2,
                                  int x3, int y3, int returnX, int returnY, int duration);
}