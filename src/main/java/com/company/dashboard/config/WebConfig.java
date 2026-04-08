package com.company.dashboard.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${onboarding.upload.dir:C:/onboard/uploads}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String location = "file:" + uploadDir;
        if (!location.endsWith("/")) {
            location += "/";
        }
        
        registry.addResourceHandler("/files/**")
                .addResourceLocations(location);
    }

    // CORS is now entirely managed centrally by SecurityConfig
}



