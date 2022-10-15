package com.udla.ingweb.backend.Security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@Configuration
@EnableWebMvc
public class InterceptorJwtIOConfig implements WebMvcConfigurer {

    @Value("${ingweb.jwt.security.enable:false}")
    private boolean securityEnabled;

    @Autowired
    private InterceptorJwtIO interceptorJwtIO;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (securityEnabled){
            registry.addInterceptor(interceptorJwtIO);
        }
    }


}
