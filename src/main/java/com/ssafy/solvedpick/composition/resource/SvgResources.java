package com.ssafy.solvedpick.composition.resource;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@Component
public class SvgResources {

    private final Map<Character, String> letters;
    private final Map<Character, String> numbers;

    private final String space;
    private final String beach;
    private final String field;
    private final String ocean;
    private final String sand;
    private final String snow1;
    private final String snow2;
    private final String window1;
    private final String window2;
    private final String heart;

    private final String SSAFY;
    private final String newYearLuckCat;
    private final String ideaCat;
    private final String mvpCat;
    private final String cupidCat;
    private final String chocoFondueCat;
    private final String likeCat;

    private final String codingCat;
    private final String yarnBallCat;
    private final String ufoCat;
    private final String tteokgukCat;
    private final String magpieCat;
    private final String caffeineCat;
    private final String singingCat;
    private final String workCat;
    private final String diverCat;
    private final String angryCat;
    private final String chillCat;

    private final String fishbowlCat;
    private final String eatingCat;
    private final String flexCat;
    private final String boxCat;
    private final String fishingCat;
    private final String toyCat;
    private final String loafCat;
    private final String coldCat;
    private final String invisibleCat;
    private final String anonymousCat;
    private final String comfortableCat;
    private final String splashCat;
    private final String farmerCat;
    private final String bookCat;
    private final String phoneCat;
    private final String birthdayCat;
    private final String jumpCat;
    private final String sickCat;
    private final String gymCat;

    private final String springCat;
    private final String pythonCat;
    private final String tsCat;
    private final String cSharpCat;
    private final String cCat;
    private final String cPlusPlusCat;
    private final String javaCat;
    private final String cssCat;
    private final String jsCat;
    private final String htmlCat;
    private final String reactCat;
    private final String nodeCat;
    private final String djangoCat;
    private final String kotlinCat;
    private final String swiftCat;
    private final String vueCat;
    private final String rustCat;

    private final String pumpkin;
    private final String nero;
    private final String graybao;
    private final String pubao;
    private final String ninja;
    private final String navy;
    private final String apricot;
    private final String lemon;
    private final String siam;
    private final String cheese;
    private final String barcode;
    private final String vanilla;
    private final String fox;
    private final String husky;
    private final String raccoon;
    private final String night;
    private final String calico;
    private final String darkCloud;
    private final String gray;
    private final String banana;
    private final String oreo;
    private final String penguin;
    private final String grass;
    private final String cloud;
    private final String cherry;
    private final String skunk;
    private final String mackerel;
    private final String tiger;
    private final String blackie;
    private final String choco;
    private final String cream;

    private final String squashedCat;
    private final String whirlwindCat;
    private final String meltingCat;
    private final String slimeCat;
    private final String meltingSlimeCat;
    private final String sleepyCat;
    private final String jumpingCat;
    private final String tailZapCat;
    private final String sprawledCat;
    private final String errorCat;
    private final String neckPillowCat;
    private final String hillCat;
    private final String questionCat;
    private final String exclamationCat;
    private final String hissingCat;
    private final String buttCat;
    private final String rollingOverCat;
    private final String groomingCat;
    private final String crouchedCat;
    private final String flippedCat;

    private final String master;
    private final String ruby1;
    private final String ruby2;
    private final String ruby3;
    private final String ruby4;
    private final String ruby5;
    private final String diamond1;
    private final String diamond2;
    private final String diamond3;
    private final String diamond4;
    private final String diamond5;
    private final String platinum1;
    private final String platinum2;
    private final String platinum3;
    private final String platinum4;
    private final String platinum5;
    private final String gold1;
    private final String gold2;
    private final String gold3;
    private final String gold4;
    private final String gold5;
    private final String silver1;
    private final String silver2;
    private final String silver3;
    private final String silver4;
    private final String silver5;
    private final String bronze1;
    private final String bronze2;
    private final String bronze3;
    private final String bronze4;
    private final String bronze5;
    private final String unrated;

    public SvgResources(ResourceLoader resourceLoader) throws IOException {
        log.debug("Initializing SVG resources");
        this.letters = new HashMap<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            this.letters.put(c, loadSvg(resourceLoader, String.format("static/letters/%c.svg", c)));
        }
        for (char c = 'a'; c <= 'z'; c++) {
            this.letters.put(c, loadSvg(resourceLoader, String.format("static/letters/_%c.svg", c)));
        }

        this.letters.put('_', loadSvg(resourceLoader, "static/letters/underscore.svg"));

        this.numbers = new HashMap<>();
        for (char n = '0'; n <= '9'; n++) {
            this.numbers.put(n, loadSvg(resourceLoader, String.format("static/numbers/%c.svg", n)));
        }

        this.space = loadSvg(resourceLoader, "static/backgrounds/Space.svg");
        this.beach = loadSvg(resourceLoader, "static/backgrounds/Beach.svg");
        this.field = loadSvg(resourceLoader, "static/backgrounds/Field.svg");
        this.ocean = loadSvg(resourceLoader, "static/backgrounds/Ocean.svg");
        this.sand = loadSvg(resourceLoader, "static/backgrounds/Sand.svg");
        this.snow1 = loadSvg(resourceLoader, "static/backgrounds/Snow1.svg");
        this.snow2 = loadSvg(resourceLoader, "static/backgrounds/Snow2.svg");
        this.window1 = loadSvg(resourceLoader, "static/backgrounds/Window1.svg");
        this.window2 = loadSvg(resourceLoader, "static/backgrounds/Window2.svg");
        this.heart = loadSvg(resourceLoader, "static/backgrounds/Heart.svg");

        this.SSAFY = loadSvg(resourceLoader, "static/avatars/SSAFY.svg");
        this.newYearLuckCat = loadSvg(resourceLoader, "static/avatars/NewYearLuckCat.svg");
        this.ideaCat = loadSvg(resourceLoader, "static/avatars/IdeaCat.svg");
        this.mvpCat = loadSvg(resourceLoader, "static/avatars/MVPCat.svg");
        this.cupidCat = loadSvg(resourceLoader, "static/avatars/CupidCat.svg");
        this.chocoFondueCat = loadSvg(resourceLoader, "static/avatars/ChocoFondueCat.svg");
        this.likeCat = loadSvg(resourceLoader, "static/avatars/LikeCat.svg");

        this.codingCat = loadSvg(resourceLoader, "static/avatars/CodingCat.svg");
        this.yarnBallCat = loadSvg(resourceLoader, "static/avatars/YarnBallCat.svg");
        this.ufoCat = loadSvg(resourceLoader, "static/avatars/UFOCat.svg");
        this.tteokgukCat = loadSvg(resourceLoader, "static/avatars/TteokgukCat.svg");
        this.magpieCat = loadSvg(resourceLoader, "static/avatars/MagpieCat.svg");
        this.caffeineCat = loadSvg(resourceLoader, "static/avatars/CaffeineCat.svg");
        this.singingCat = loadSvg(resourceLoader, "static/avatars/SingingCat.svg");
        this.workCat = loadSvg(resourceLoader, "static/avatars/WorkCat.svg");
        this.diverCat = loadSvg(resourceLoader, "static/avatars/DiverCat.svg");
        this.angryCat = loadSvg(resourceLoader, "static/avatars/AngryCat.svg");
        this.chillCat = loadSvg(resourceLoader, "static/avatars/ChillCat.svg");

        this.fishbowlCat = loadSvg(resourceLoader, "static/avatars/FishbowlCat.svg");
        this.eatingCat = loadSvg(resourceLoader, "static/avatars/EatingCat.svg");
        this.flexCat = loadSvg(resourceLoader, "static/avatars/FlexCat.svg");
        this.boxCat = loadSvg(resourceLoader, "static/avatars/BoxCat.svg");
        this.fishingCat = loadSvg(resourceLoader, "static/avatars/FishingCat.svg");
        this.toyCat = loadSvg(resourceLoader, "static/avatars/ToyCat.svg");
        this.loafCat = loadSvg(resourceLoader, "static/avatars/LoafCat.svg");
        this.coldCat = loadSvg(resourceLoader, "static/avatars/ColdCat.svg");
        this.invisibleCat = loadSvg(resourceLoader, "static/avatars/InvisibleCat.svg");
        this.anonymousCat = loadSvg(resourceLoader, "static/avatars/AnonymousCat.svg");
        this.comfortableCat = loadSvg(resourceLoader, "static/avatars/ComfortableCat.svg");
        this.splashCat = loadSvg(resourceLoader, "static/avatars/SplashCat.svg");
        this.farmerCat = loadSvg(resourceLoader, "static/avatars/FarmerCat.svg");
        this.bookCat = loadSvg(resourceLoader, "static/avatars/BookCat.svg");
        this.phoneCat = loadSvg(resourceLoader, "static/avatars/PhoneCat.svg");
        this.birthdayCat = loadSvg(resourceLoader, "static/avatars/BirthdayCat.svg");
        this.jumpCat = loadSvg(resourceLoader, "static/avatars/JumpCat.svg");
        this.sickCat = loadSvg(resourceLoader, "static/avatars/SickCat.svg");
        this.gymCat = loadSvg(resourceLoader, "static/avatars/GymCat.svg");

        this.springCat = loadSvg(resourceLoader, "static/avatars/SpringCat.svg");
        this.pythonCat = loadSvg(resourceLoader, "static/avatars/PythonCat.svg");
        this.tsCat = loadSvg(resourceLoader, "static/avatars/TSCat.svg");
        this.cSharpCat = loadSvg(resourceLoader, "static/avatars/CSharpCat.svg");
        this.cCat = loadSvg(resourceLoader, "static/avatars/CCat.svg");
        this.cPlusPlusCat = loadSvg(resourceLoader, "static/avatars/CPlusPlusCat.svg");
        this.javaCat = loadSvg(resourceLoader, "static/avatars/JavaCat.svg");
        this.cssCat = loadSvg(resourceLoader, "static/avatars/CSSCat.svg");
        this.jsCat = loadSvg(resourceLoader, "static/avatars/JSCat.svg");
        this.htmlCat = loadSvg(resourceLoader, "static/avatars/HTMLCat.svg");
        this.reactCat = loadSvg(resourceLoader, "static/avatars/ReactCat.svg");
        this.nodeCat = loadSvg(resourceLoader, "/static/avatars/NodeCat.svg");
        this.djangoCat = loadSvg(resourceLoader, "/static/avatars/DjangoCat.svg");
        this.kotlinCat = loadSvg(resourceLoader, "/static/avatars/KotlinCat.svg");
        this.swiftCat = loadSvg(resourceLoader, "/static/avatars/SwiftCat.svg");
        this.vueCat = loadSvg(resourceLoader, "static/avatars/VueCat.svg");
        this.rustCat = loadSvg(resourceLoader, "static/avatars/RustCat.svg");

        this.pumpkin = loadSvg(resourceLoader, "static/avatars/Pumpkin.svg");
        this.nero = loadSvg(resourceLoader, "static/avatars/Nero.svg");
        this.graybao = loadSvg(resourceLoader, "static/avatars/Graybao.svg");
        this.pubao = loadSvg(resourceLoader, "static/avatars/Pubao.svg");
        this.ninja = loadSvg(resourceLoader, "static/avatars/Ninja.svg");
        this.navy = loadSvg(resourceLoader, "static/avatars/Navy.svg");
        this.apricot = loadSvg(resourceLoader, "static/avatars/Apricot.svg");
        this.lemon = loadSvg(resourceLoader, "static/avatars/Lemon.svg");
        this.siam = loadSvg(resourceLoader, "static/avatars/Siam.svg");
        this.cheese = loadSvg(resourceLoader, "static/avatars/Cheese.svg");
        this.barcode = loadSvg(resourceLoader, "static/avatars/Barcode.svg");
        this.vanilla = loadSvg(resourceLoader, "static/avatars/Vanilla.svg");
        this.fox = loadSvg(resourceLoader, "static/avatars/Fox.svg");
        this.husky = loadSvg(resourceLoader, "static/avatars/Husky.svg");
        this.raccoon = loadSvg(resourceLoader, "static/avatars/Raccoon.svg");
        this.night = loadSvg(resourceLoader, "static/avatars/Night.svg");
        this.calico = loadSvg(resourceLoader, "static/avatars/Calico.svg");
        this.darkCloud = loadSvg(resourceLoader, "static/avatars/DarkCloud.svg");
        this.gray = loadSvg(resourceLoader, "static/avatars/Gray.svg");
        this.banana = loadSvg(resourceLoader, "static/avatars/Banana.svg");
        this.oreo = loadSvg(resourceLoader, "static/avatars/Oreo.svg");
        this.penguin = loadSvg(resourceLoader, "static/avatars/Penguin.svg");
        this.grass = loadSvg(resourceLoader, "static/avatars/Grass.svg");
        this.cloud = loadSvg(resourceLoader, "static/avatars/Cloud.svg");
        this.cherry = loadSvg(resourceLoader, "static/avatars/Cherry.svg");
        this.skunk = loadSvg(resourceLoader, "static/avatars/Skunk.svg");
        this.mackerel = loadSvg(resourceLoader, "static/avatars/Mackerel.svg");
        this.tiger = loadSvg(resourceLoader, "static/avatars/Tiger.svg");
        this.blackie = loadSvg(resourceLoader, "static/avatars/Blackie.svg");
        this.choco = loadSvg(resourceLoader, "static/avatars/Choco.svg");
        this.cream = loadSvg(resourceLoader, "static/avatars/Cream.svg");

        this.squashedCat = loadSvg(resourceLoader, "static/avatars/SquashedCat.svg");
        this.whirlwindCat = loadSvg(resourceLoader, "static/avatars/WhirlwindCat.svg");
        this.meltingCat = loadSvg(resourceLoader, "static/avatars/MeltingCat.svg");
        this.slimeCat = loadSvg(resourceLoader, "static/avatars/SlimeCat.svg");
        this.meltingSlimeCat = loadSvg(resourceLoader, "static/avatars/MeltingSlimeCat.svg");
        this.sleepyCat = loadSvg(resourceLoader, "static/avatars/SleepyCat.svg");
        this.jumpingCat = loadSvg(resourceLoader, "static/avatars/JumpingCat.svg");
        this.tailZapCat = loadSvg(resourceLoader, "static/avatars/TailZapCat.svg");
        this.sprawledCat = loadSvg(resourceLoader, "static/avatars/SprawledCat.svg");
        this.errorCat = loadSvg(resourceLoader, "static/avatars/ErrorCat.svg");
        this.neckPillowCat = loadSvg(resourceLoader, "static/avatars/NeckPillowCat.svg");
        this.hillCat = loadSvg(resourceLoader, "static/avatars/HillCat.svg");
        this.questionCat = loadSvg(resourceLoader, "static/avatars/QuestionCat.svg");
        this.exclamationCat = loadSvg(resourceLoader, "static/avatars/ExclamationCat.svg");
        this.hissingCat = loadSvg(resourceLoader, "static/avatars/HissingCat.svg");
        this.buttCat = loadSvg(resourceLoader, "static/avatars/ButtCat.svg");
        this.rollingOverCat = loadSvg(resourceLoader, "static/avatars/RollingOverCat.svg");
        this.groomingCat = loadSvg(resourceLoader, "static/avatars/GroomingCat.svg");
        this.crouchedCat = loadSvg(resourceLoader, "static/avatars/CrouchedCat.svg");
        this.flippedCat = loadSvg(resourceLoader, "static/avatars/FlippedCat.svg");

        this.master = loadSvg(resourceLoader, "static/tiers/Master.svg");
        this.ruby1 = loadSvg(resourceLoader, "static/tiers/Ruby1.svg");
        this.ruby2 = loadSvg(resourceLoader, "static/tiers/Ruby2.svg");
        this.ruby3 = loadSvg(resourceLoader, "static/tiers/Ruby3.svg");
        this.ruby4 = loadSvg(resourceLoader, "static/tiers/Ruby4.svg");
        this.ruby5 = loadSvg(resourceLoader, "static/tiers/Ruby5.svg");
        this.diamond1 = loadSvg(resourceLoader, "static/tiers/Diamond1.svg");
        this.diamond2 = loadSvg(resourceLoader, "static/tiers/Diamond2.svg");
        this.diamond3 = loadSvg(resourceLoader, "static/tiers/Diamond3.svg");
        this.diamond4 = loadSvg(resourceLoader, "static/tiers/Diamond4.svg");
        this.diamond5 = loadSvg(resourceLoader, "static/tiers/Diamond5.svg");
        this.platinum1 = loadSvg(resourceLoader, "static/tiers/Platinum1.svg");
        this.platinum2 = loadSvg(resourceLoader, "static/tiers/Platinum2.svg");
        this.platinum3 = loadSvg(resourceLoader, "static/tiers/Platinum3.svg");
        this.platinum4 = loadSvg(resourceLoader, "static/tiers/Platinum4.svg");
        this.platinum5 = loadSvg(resourceLoader, "static/tiers/Platinum5.svg");
        this.gold1 = loadSvg(resourceLoader, "static/tiers/Gold1.svg");
        this.gold2 = loadSvg(resourceLoader, "static/tiers/Gold2.svg");
        this.gold3 = loadSvg(resourceLoader, "static/tiers/Gold3.svg");
        this.gold4 = loadSvg(resourceLoader, "static/tiers/Gold4.svg");
        this.gold5 = loadSvg(resourceLoader, "static/tiers/Gold5.svg");
        this.silver1 = loadSvg(resourceLoader, "static/tiers/Silver1.svg");
        this.silver2 = loadSvg(resourceLoader, "static/tiers/Silver2.svg");
        this.silver3 = loadSvg(resourceLoader, "static/tiers/Silver3.svg");
        this.silver4 = loadSvg(resourceLoader, "static/tiers/Silver4.svg");
        this.silver5 = loadSvg(resourceLoader, "static/tiers/Silver5.svg");
        this.bronze1 = loadSvg(resourceLoader, "static/tiers/Bronze1.svg");
        this.bronze2 = loadSvg(resourceLoader, "static/tiers/Bronze2.svg");
        this.bronze3 = loadSvg(resourceLoader, "static/tiers/Bronze3.svg");
        this.bronze4 = loadSvg(resourceLoader, "static/tiers/Bronze4.svg");
        this.bronze5 = loadSvg(resourceLoader, "static/tiers/Bronze5.svg");
        this.unrated = loadSvg(resourceLoader, "static/tiers/Unrated.svg");

        log.debug("Svg resources initialized successfully");
    }

    private String loadSvg(ResourceLoader resourceLoader, String path) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + path);
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
}
