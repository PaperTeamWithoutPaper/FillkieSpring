package com.fillkie.security.advice;

import com.fillkie.controller.response.DefaultResponse;
import com.fillkie.controller.response.ResponseFail;
import com.fillkie.controller.response.ResponseFailRedirect;
import com.fillkie.security.advice.exception.AccessTokenExpiredException;
import com.fillkie.security.advice.exception.RefreshTokenExpiredException;
import com.fillkie.security.advice.exception.TokenEmptyException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    // 토큰 없음
    @ExceptionHandler(TokenEmptyException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<? extends DefaultResponse> handleTokenEmptyException(
        HttpServletRequest request, TokenEmptyException e){
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ResponseFailRedirect(false, 420, e.getMessage(), request.getRequestURI()));
    }

    // AccessToken 만료
    @ExceptionHandler(AccessTokenExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<? extends DefaultResponse> handleAccessTokenExpiredException(HttpServletRequest request, AccessTokenExpiredException e){
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ResponseFailRedirect(false, 421, e.getMessage(), request.getRequestURI()));
    }

    // RefreshToken 만료
    @ExceptionHandler(RefreshTokenExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<? extends DefaultResponse> handleRefreshTokenExpiredException(HttpServletRequest request, RefreshTokenExpiredException e){
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ResponseFailRedirect(false, 422, e.getMessage(), request.getRequestURI()));
    }

}
