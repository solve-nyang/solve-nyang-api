package com.ssafy.solvedpick.common.enums;

import com.ssafy.solvedpick.core.resource.SvgResources;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum AvatarType {
    SSAFY("SSAFY"),

    CODING_CAT("CodingCat"),
    YARN_BALL_CAT("YarnBallCat"),
    UFO_CAT("UFOCat"),

    FISHBOWL_CAT("FishbowlCat"),
    EATING_CAT("EatingCat"),
    FLEX_CAT("FlexCat"),
    BOX_CAT("BoxCat"),
    FISHING_CAT("FishingCat"),
    TOY_CAT("ToyCat"),
    LOAF_CAT("LoafCat"),
    COLD_CAT("ColdCat"),
    INVISIBLE_CAT("InvisibleCat"),
    ANONYMOUS_CAT("AnonymousCat"),
    COMFORTABLE_CAT("ComfortableCat"),


    SPRING_CAT("SpringCat"),
    PYTHON_CAT("PythonCat"),
    TS_CAT("TSCat"),
    C_SHARP_CAT("CSharpCat"),
    C_CAT("CCat"),
    C_PLUS_PLUS_CAT("CPlusPlusCat"),
    JAVA_CAT("JavaCat"),
    CSS_CAT("CSSCat"),
    JS_CAT("JSCat"),
    HTML_CAT("HTMLCat"),

    PUMPKIN("Pumpkin"),
    NERO("Nero"),
    GRAYBAO("Graybao"),
    PUBAO("Pubao"),
    NINJA("Ninja"),
    NAVY("Navy"),
    APRICOT("Apricot"),
    LEMON("Lemon"),
    SIAM("Siam"),
    CHEESE("Cheese"),
    BARCODE("Barcode"),
    VANILLA("Vanilla"),
    FOX("Fox"),
    HUSKY("Husky"),
    RACCOON("Raccoon"),
    NIGHT("Night"),
    CALICO("Calico"),
    DARK_CLOUD("DarkCloud"),
    GRAY("Gray"),
    BANANA("Banana"),
    OREO("Oreo"),
    PENGUIN("Penguin"),
    GRASS("Grass"),
    CLOUD("Cloud"),
    CHERRY("Cherry"),
    SKUNK("Skunk"),
    MACKEREL("Mackerel"),
    TIGER("Tiger"),
    BLACKIE("Blackie"),
    CHOCO("Choco"),
    CREAM("Cream"),

    SQUASHED_CAT("SquashedCat"),
    WHIRLWIND_CAT("WhirlwindCat"),
    MELTING_CAT("MeltingCat"),
    SLIME_CAT("SlimeCat"),
    MELTING_SLIME_CAT("MeltingSlimeCat"),
    SLEEPY_CAT("SleepyCat"),
    JUMPING_CAT("JumpingCat"),
    TAIL_ZAP_CAT("TailZapCat"),
    SPRAWLED_CAT("SprawledCat"),
    ERROR_CAT("ErrorCat"),
    NECK_PILLOW_CAT("NeckPillowCat"),
    HILL_CAT("HillCat"),
    QUESTION_CAT("QuestionCat"),
    EXCLAMATION_CAT("ExclamationCat"),
    HISSING_CAT("HissingCat"),
    BUTT_CAT("ButtCat"),
    ROLLING_OVER_CAT("RollingOverCat"),
    GROOMING_CAT("GroomingCat"),
    CROUCHED_CAT("CrouchedCat"),
    FLIPPED_CAT("FlippedCat");

    private final String name;

    public String getSvgContent(SvgResources svgResources){
        return switch (this) {
            case CODING_CAT -> svgResources.getCodingCat();
            case YARN_BALL_CAT -> svgResources.getYarnBallCat();
            case UFO_CAT -> svgResources.getUfoCat();

            case FISHBOWL_CAT -> svgResources.getFishbowlCat();
            case EATING_CAT -> svgResources.getEatingCat();
            case FLEX_CAT -> svgResources.getFlexCat();
            case BOX_CAT -> svgResources.getBoxCat();
            case FISHING_CAT -> svgResources.getFishingCat();
            case TOY_CAT -> svgResources.getToyCat();
            case LOAF_CAT -> svgResources.getLoafCat();
            case COLD_CAT -> svgResources.getColdCat();
            case INVISIBLE_CAT -> svgResources.getInvisibleCat();
            case ANONYMOUS_CAT -> svgResources.getAnonymousCat();
            case COMFORTABLE_CAT -> svgResources.getComfortableCat();
            case SSAFY -> svgResources.getSSAFY();

            case SPRING_CAT -> svgResources.getSpringCat();
            case PYTHON_CAT -> svgResources.getPythonCat();
            case TS_CAT -> svgResources.getTsCat();
            case C_SHARP_CAT -> svgResources.getCSharpCat();
            case C_CAT -> svgResources.getCCat();
            case C_PLUS_PLUS_CAT -> svgResources.getCPlusPlusCat();
            case JAVA_CAT -> svgResources.getJavaCat();
            case CSS_CAT -> svgResources.getCssCat();
            case JS_CAT -> svgResources.getJsCat();
            case HTML_CAT -> svgResources.getHtmlCat();

            case PUMPKIN -> svgResources.getPumpkin();
            case NERO -> svgResources.getNero();
            case GRAYBAO -> svgResources.getGraybao();
            case PUBAO -> svgResources.getPubao();
            case NINJA -> svgResources.getNinja();
            case NAVY -> svgResources.getNavy();
            case APRICOT -> svgResources.getApricot();
            case LEMON -> svgResources.getLemon();
            case SIAM -> svgResources.getSiam();
            case CHEESE -> svgResources.getCheese();
            case BARCODE -> svgResources.getBarcode();
            case VANILLA -> svgResources.getVanilla();
            case FOX -> svgResources.getFox();
            case HUSKY -> svgResources.getHusky();
            case RACCOON -> svgResources.getRaccoon();
            case NIGHT -> svgResources.getNight();
            case CALICO -> svgResources.getCalico();
            case DARK_CLOUD -> svgResources.getDarkCloud();
            case GRAY -> svgResources.getGray();
            case BANANA -> svgResources.getBanana();
            case OREO -> svgResources.getOreo();
            case PENGUIN -> svgResources.getPenguin();
            case GRASS -> svgResources.getGrass();
            case CLOUD -> svgResources.getCloud();
            case CHERRY -> svgResources.getCherry();
            case SKUNK -> svgResources.getSkunk();
            case MACKEREL -> svgResources.getMackerel();
            case TIGER -> svgResources.getTiger();
            case BLACKIE -> svgResources.getBlackie();
            case CHOCO -> svgResources.getChoco();
            case CREAM -> svgResources.getCream();

            case SQUASHED_CAT -> svgResources.getSquashedCat();
            case WHIRLWIND_CAT -> svgResources.getWhirlwindCat();
            case MELTING_CAT -> svgResources.getMeltingCat();
            case SLIME_CAT -> svgResources.getSlimeCat();
            case MELTING_SLIME_CAT -> svgResources.getMeltingSlimeCat();
            case SLEEPY_CAT -> svgResources.getSleepyCat();
            case JUMPING_CAT -> svgResources.getJumpingCat();
            case TAIL_ZAP_CAT -> svgResources.getTailZapCat();
            case SPRAWLED_CAT -> svgResources.getSprawledCat();
            case ERROR_CAT -> svgResources.getErrorCat();
            case NECK_PILLOW_CAT -> svgResources.getNeckPillowCat();
            case HILL_CAT -> svgResources.getHillCat();
            case QUESTION_CAT -> svgResources.getQuestionCat();
            case EXCLAMATION_CAT -> svgResources.getExclamationCat();
            case HISSING_CAT -> svgResources.getHissingCat();
            case BUTT_CAT -> svgResources.getButtCat();
            case ROLLING_OVER_CAT -> svgResources.getRollingOverCat();
            case GROOMING_CAT -> svgResources.getGroomingCat();
            case CROUCHED_CAT -> svgResources.getCrouchedCat();
            case FLIPPED_CAT -> svgResources.getFlippedCat();
        };
    }

    public static AvatarType fromValue(String value) {
        return Arrays.stream(values())
                .filter(type -> type.getName().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid AvatarType: " + value));
    }


}
