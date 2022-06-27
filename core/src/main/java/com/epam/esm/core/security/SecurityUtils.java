package com.epam.esm.core.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Getter

public class SecurityUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.accessToken.lifetime}")
    private long accessTokenLifetime;

    @Value("${jwt.refreshToken.lifetime}")
    private long refreshTokenLifetime;
}
