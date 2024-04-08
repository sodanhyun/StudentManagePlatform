package com.codehows.smp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private String path;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        path = "home";
        registry.addResourceHandler("/codehowsSMP/resource/image/**").addResourceLocations("file:///"+ path +"/");
    }
}
