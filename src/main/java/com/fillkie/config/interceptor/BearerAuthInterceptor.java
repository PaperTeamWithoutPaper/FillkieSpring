package com.fillkie.config.interceptor;

import com.fillkie.advice.exception.TokenEmptyException;
import com.fillkie.config.auth.AuthorizationExtractor;
import com.fillkie.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT 검증 Interceptor
 */
@Component
@RequiredArgsConstructor
public class BearerAuthInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(BearerAuthInterceptor.class);

    private final AuthorizationExtractor authorizationExtractor;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info(">>> interceptor.preHandle 호출");
        String token = authorizationExtractor.extract(request, "Bearer");
        if (token.isEmpty()) {
            throw new TokenEmptyException();
        }

        // !!!Refresh Token으로 재발급?
        if (!jwtTokenProvider.validateToken(token)) {
            throw new IllegalArgumentException("유효하지 않은 토큰");
        }

        String email = String.valueOf(jwtTokenProvider.getSubject(token));
        request.setAttribute("email", email);
        return true;
    }
}
