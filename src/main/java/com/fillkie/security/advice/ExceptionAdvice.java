package com.fillkie.security.advice;

import com.fillkie.controller.response.DefaultResponse;
import com.fillkie.controller.response.ResponseFail;
import com.fillkie.controller.response.ResponseFailRedirect;
import com.fillkie.security.advice.exception.AccessTokenExpiredException;
import com.fillkie.security.advice.exception.NoPermissionException;
import com.fillkie.security.advice.exception.RefreshTokenExpiredException;
import com.fillkie.security.advice.exception.TokenEmptyException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    // 토큰 없음
    @ExceptionHandler(TokenEmptyException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<? extends DefaultResponse> handleTokenEmptyException(
        HttpServletRequest request, TokenEmptyException e){
        log.error("request URI", request.getRequestURI());
        log.error("TokenEmptyException : exceptionHandler 호출");
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ResponseFailRedirect(false, 420, e.getMessage(), request.getRequestURI()));
    }

    // AccessToken 만료
    @ExceptionHandler(AccessTokenExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<? extends DefaultResponse> handleAccessTokenExpiredException(HttpServletRequest request, AccessTokenExpiredException e){
        log.error("request URI", request.getRequestURI());
        log.error("AccessTokenExpiredException : exceptionHandler 호출");
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ResponseFailRedirect(false, 421, e.getMessage(), request.getRequestURI()));
    }

    // RefreshToken 만료
    @ExceptionHandler(RefreshTokenExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<? extends DefaultResponse> handleRefreshTokenExpiredException(HttpServletRequest request, RefreshTokenExpiredException e){
        log.error("request URI", request.getRequestURI());
        log.error("RefreshTokenExpiredException : exceptionHandler 호출");
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ResponseFailRedirect(false, 422, e.getMessage(), request.getRequestURI()));
    }

    // -------------------------------------------Permission에 대한 예외 처리-------------------------------------------

    /**
     * UpdatePermissionInterceptor : 팀의 Group, User에 대한 권한 update 권한 인가
     */
    @ExceptionHandler(NoPermissionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ResponseEntity<? extends DefaultResponse> handleUpdatePermissionException(HttpServletRequest request, NoPermissionException e){
        log.error("request URI", request.getRequestURI());
        log.error("NoPermissionException : exceptionHandler 호출");
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(new ResponseFail(false, 403, e.getMessage()));
    }

}
