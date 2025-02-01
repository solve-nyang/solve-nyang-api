package com.ssafy.solvedpick.composition.resource;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Getter
@Component
public class SvgResources {

    private final String baseField;
    private final String spaceField;

    private final String SSAFY;
    private final String newYearLuckCat;

    private final String codingCat;
    private final String yarnBallCat;
    private final String ufoCat;
    private final String tteokgukCat;
    private final String magpieCat;

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

    public SvgResources(ResourceLoader resourceLoader) throws IOException {
        log.debug("Initializing SVG resources");

        this.baseField = loadSvg(resourceLoader, "static/backgrounds/BaseField.svg");
        this.spaceField = loadSvg(resourceLoader, "static/backgrounds/SpaceField.svg");

        this.SSAFY = loadSvg(resourceLoader, "static/avatars/SSAFY.svg");
        this.newYearLuckCat = loadSvg(resourceLoader, "static/avatars/NewYearLuckCat.svg");

        this.codingCat = loadSvg(resourceLoader, "static/avatars/CodingCat.svg");
        this.yarnBallCat = loadSvg(resourceLoader, "static/avatars/YarnBallCat.svg");
        this.ufoCat = loadSvg(resourceLoader, "static/avatars/UFOCat.svg");
        this.tteokgukCat = loadSvg(resourceLoader, "static/avatars/TteokgukCat.svg");
        this.magpieCat = loadSvg(resourceLoader, "static/avatars/MagpieCat.svg");

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

        log.debug("Svg resources initialized successfully");
    }

    private String loadSvg(ResourceLoader resourceLoader, String path) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + path);
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
}
