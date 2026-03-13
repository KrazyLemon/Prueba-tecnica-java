package com.prueba.main.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
public class CryptoConfig {

    @Value("${aes.secret}")
    private String secret;

    @Bean
    public SecretKey secretKey() {
        return new SecretKeySpec(secret.getBytes(), "AES");
    }
}