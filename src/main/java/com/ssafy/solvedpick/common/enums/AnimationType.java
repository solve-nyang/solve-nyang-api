package com.ssafy.solvedpick.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AnimationType {
    SPACE_FLOAT("""
    <animateMotion path=\"M %d %d C %d %d %d %d %d %d L %d %d\" 
    dur=\"%ds\" repeatCount=\"indefinite\" keyPoints=\"0;1;0\" keyTimes=\"0;0.5;1\" calcMode=\"linear\"/>
    """),
    SPACE_ROTATE("<animateTransform attributeName=\"transform\" type=\"rotate\" dur=\"%ds\" values=\"0 %d %d; 360 %d %d\" additive=\"sum\" repeatCount=\"indefinite\" />"),

    BASE_BOUNCE("""
        <animateTransform attributeName=\"transform\" 
        type=\"translate\" dur=\"%ds\" values=\"%d,%d; %d,%d; %d,%d\" 
        additive=\"sum\" repeatCount=\"indefinite\" />
    """);

    private final String animation;

    public String format(Object... args) {
        return String.format(animation, args);
    }
}
