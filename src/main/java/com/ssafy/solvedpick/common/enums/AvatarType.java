package com.ssafy.solvedpick.common.enums;

import com.ssafy.solvedpick.composition.resource.SvgResources;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum AvatarType {
    SSAFY("SSAFY", 334, 334, AnimationType.FLOAT),

    CODING_CAT("CodingCat", 295, 295, AnimationType.FLOAT),
    YARN_BALL_CAT("YarnBallCat", 312, 311, AnimationType.FLOAT),
    UFO_CAT("UFOCat", 312, 311, AnimationType.FLOAT),

    FISHBOWL_CAT("FishbowlCat", 311, 311, AnimationType.FLOAT),
    EATING_CAT("EatingCat", 312, 311, AnimationType.FLOAT),
    FLEX_CAT("FlexCat", 311, 312, AnimationType.FLOAT),
    BOX_CAT("BoxCat", 312, 312, AnimationType.FLOAT),
    FISHING_CAT("FishingCat", 312, 312, AnimationType.FLOAT),
    TOY_CAT("ToyCat", 311, 311, AnimationType.FLOAT),
    LOAF_CAT("LoafCat", 312, 312, AnimationType.FLOAT),
    COLD_CAT("ColdCat", 312, 312, AnimationType.FLOAT),
    INVISIBLE_CAT("InvisibleCat", 312, 312, AnimationType.FLOAT),
    ANONYMOUS_CAT("AnonymousCat", 295, 295, AnimationType.FLOAT),
    COMFORTABLE_CAT("ComfortableCat", 312, 312, AnimationType.FLOAT),


    SPRING_CAT("SpringCat", 312, 312, AnimationType.FLOAT),
    PYTHON_CAT("PythonCat", 312, 312, AnimationType.FLOAT),
    TS_CAT("TSCat", 312, 312, AnimationType.FLOAT),
    C_SHARP_CAT("CSharpCat", 312, 312, AnimationType.FLOAT),
    C_CAT("CCat", 312, 312, AnimationType.FLOAT),
    C_PLUS_PLUS_CAT("CPlusPlusCat", 312, 312, AnimationType.FLOAT),
    JAVA_CAT("JavaCat", 312, 312, AnimationType.FLOAT),
    CSS_CAT("CSSCat", 312, 312, AnimationType.FLOAT),
    JS_CAT("JSCat", 312, 312, AnimationType.FLOAT),
    HTML_CAT("HTMLCat", 312, 312, AnimationType.FLOAT),

    PUMPKIN("Pumpkin", 295, 295, AnimationType.FLOAT),
    NERO("Nero", 295, 294, AnimationType.FLOAT),
    GRAYBAO("Graybao", 295, 294, AnimationType.FLOAT),
    PUBAO("Pubao", 295, 294, AnimationType.FLOAT),
    NINJA("Ninja", 295, 294, AnimationType.FLOAT),
    NAVY("Navy", 295, 294, AnimationType.FLOAT),
    APRICOT("Apricot", 295, 295, AnimationType.FLOAT),
    LEMON("Lemon", 295, 295, AnimationType.FLOAT),
    SIAM("Siam", 295, 295, AnimationType.FLOAT),
    CHEESE("Cheese", 295, 295, AnimationType.FLOAT),
    BARCODE("Barcode", 295, 295, AnimationType.FLOAT),
    VANILLA("Vanilla", 295, 295, AnimationType.FLOAT),
    FOX("Fox", 295, 295, AnimationType.FLOAT),
    HUSKY("Husky", 295, 295, AnimationType.FLOAT),
    RACCOON("Raccoon", 295, 295, AnimationType.FLOAT),
    NIGHT("Night", 295, 295, AnimationType.FLOAT),
    CALICO("Calico", 294, 294, AnimationType.FLOAT),
    DARK_CLOUD("DarkCloud", 295, 295, AnimationType.FLOAT),
    GRAY("Gray", 295, 295, AnimationType.FLOAT),
    BANANA("Banana", 295, 295, AnimationType.FLOAT),
    OREO("Oreo", 295, 295, AnimationType.FLOAT),
    PENGUIN("Penguin", 295, 294, AnimationType.FLOAT),
    GRASS("Grass", 294, 295, AnimationType.FLOAT),
    CLOUD("Cloud", 295, 295, AnimationType.FLOAT),
    CHERRY("Cherry", 295, 295, AnimationType.FLOAT),
    SKUNK("Skunk", 295, 294, AnimationType.FLOAT),
    MACKEREL("Mackerel", 295, 295, AnimationType.FLOAT),
    TIGER("Tiger", 295, 295, AnimationType.FLOAT),
    BLACKIE("Blackie", 295, 295, AnimationType.FLOAT),
    CHOCO("Choco", 295, 295, AnimationType.FLOAT),
    CREAM("Cream", 295, 295, AnimationType.FLOAT),

    SQUASHED_CAT("SquashedCat", 334, 342, AnimationType.FLOAT),
    WHIRLWIND_CAT("WhirlwindCat", 335, 342, AnimationType.FLOAT),
    MELTING_CAT("MeltingCat", 334, 342, AnimationType.FLOAT),
    SLIME_CAT("SlimeCat", 335, 342, AnimationType.FLOAT),
    MELTING_SLIME_CAT("MeltingSlimeCat", 334, 342, AnimationType.FLOAT),
    SLEEPY_CAT("SleepyCat",334, 343, AnimationType.FLOAT),
    JUMPING_CAT("JumpingCat", 335, 343, AnimationType.FLOAT),
    TAIL_ZAP_CAT("TailZapCat", 334, 343, AnimationType.FLOAT),
    SPRAWLED_CAT("SprawledCat", 335, 343, AnimationType.FLOAT),
    ERROR_CAT("ErrorCat", 334, 343, AnimationType.FLOAT),
    NECK_PILLOW_CAT("NeckPillowCat", 334, 343, AnimationType.FLOAT),
    HILL_CAT("HillCat", 335, 343, AnimationType.FLOAT),
    QUESTION_CAT("QuestionCat",334, 343, AnimationType.FLOAT),
    EXCLAMATION_CAT("ExclamationCat", 335, 343, AnimationType.FLOAT),
    HISSING_CAT("HissingCat", 334, 343, AnimationType.FLOAT),
    BUTT_CAT("ButtCat", 334, 334, AnimationType.FLOAT),
    ROLLING_OVER_CAT("RollingOverCat", 335, 342, AnimationType.FLOAT),
    GROOMING_CAT("GroomingCat", 334, 342, AnimationType.FLOAT),
    CROUCHED_CAT("CrouchedCat", 335, 342, AnimationType.FLOAT),
    FLIPPED_CAT("FlippedCat", 334, 342, AnimationType.FLOAT);

    private final String name;
    private final int width;
    private final int height;
    private final AnimationType animationType;

    public String getSvgContent(SvgResources svgResources){
        return switch (this) {
            case SSAFY -> svgResources.getSSAFY();

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
