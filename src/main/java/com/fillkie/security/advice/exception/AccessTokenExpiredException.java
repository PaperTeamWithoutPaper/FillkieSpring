package com.fillkie.security.advice.exception;

import io.jsonwebtoken.JwtException;

public class AccessTokenExpiredException extends JwtException {

    public AccessTokenExpiredException(String message) {
        super(message);
    }

    public AccessTokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
