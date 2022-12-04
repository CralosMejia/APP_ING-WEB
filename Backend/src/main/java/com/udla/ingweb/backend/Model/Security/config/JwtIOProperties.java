package com.udla.ingweb.backend.Model.Security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "ingweb.jwt")
public class JwtIOProperties {

    private Securtity security;
    private Token token;
    private Excluded excluded;

    @Data
    public static class Securtity{
        private boolean enable;
    }

    @Data
    public static class Token{
        private Auth auth;
        private String secret;
        private int expiresIn;
    }

    @Data
    public static class Auth{
        private String path;
    }

    @Data
    private static class Excluded{
        private String path;
    }


}
