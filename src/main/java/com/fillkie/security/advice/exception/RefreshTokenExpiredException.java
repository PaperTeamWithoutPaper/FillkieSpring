package com.fillkie.security.advice.exception;

import io.jsonwebtoken.JwtException;

public class RefreshTokenExpiredException extends JwtException {

    public RefreshTokenExpiredException(String message) {
        super(message);
    }

    public RefreshTokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
