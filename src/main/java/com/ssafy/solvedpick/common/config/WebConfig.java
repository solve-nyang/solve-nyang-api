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
    @Value("${CORS.ALLOWED_ORIGINS}")
    private String[] allowedOrigins;
    @Value("${CORS.EXTENSION_PATH}")
    private String extensionPath;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(extensionPath)
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);

        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);

    }
}