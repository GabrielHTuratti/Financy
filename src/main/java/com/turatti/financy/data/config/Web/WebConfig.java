package com.turatti.financy.data.config.Web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080") // Allow your frontend origin
                .allowCredentials(true) // Allow cookies
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}