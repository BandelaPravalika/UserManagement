package com.company.dashboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    // serve files from your uploads folder when frontend calls /api/files/**
	    registry.addResourceHandler("/api/files/**")
	            .addResourceLocations("file:C:/onboard/uploads/");
	}
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        // allow frontend requests from port 5173
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("*");
    }


}
