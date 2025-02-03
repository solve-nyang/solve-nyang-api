package com.ssafy.solvedpick.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${FRONT.URL}")
    private String FRONT_URL;
    @Value("${FRONT.DEV}")
    private String DEV_URL;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/user/me/extension")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);

        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:5173",
                        "https://github.com",
                        "https://raw.githubusercontent.com",
                        "https://user-images.githubusercontent.com",
                        "https://camo.githubusercontent.com",
                        "https://www.acmicpc.net/",
                        FRONT_URL,
                        DEV_URL
                )
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("New-Access-Token")
                .allowCredentials(true);

    }
}