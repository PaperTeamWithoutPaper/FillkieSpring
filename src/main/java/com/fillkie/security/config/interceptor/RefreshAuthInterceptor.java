package com.fillkie.security.config.interceptor;

import com.fillkie.security.advice.exception.RefreshTokenExpiredException;
import com.fillkie.security.advice.exception.TokenEmptyException;
import com.fillkie.security.config.auth.AuthorizationExtractor;
import com.fillkie.security.config.jwt.JwtTokenProvider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * RefreshToken 검증 Interceptor
 * UserController : checkRefreshTokenUser
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RefreshAuthInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(BearerAuthInterceptor.class);

    private static final String REFRESH = "Refresh";
    private final AuthorizationExtractor authorizationExtractor;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) {
        logger.info(">>> refreshInterceptor.preHandle 호출");
        logger.info(">>> refreshInterceptor method : {}", request.getMethod());
        logger.info(">>> refreshInterceptor 호출 url : {}", request.getRequestURI());
        logger.info(">>> refreshInterceptor ip1 url : {}", request.getRemoteAddr());
        logger.info(">>> refreshInterceptor ip2 url : {}", request.getRemoteHost());
        String token = authorizationExtractor.extract(request, "Bearer", REFRESH);


        // RefreshToken Empty 상황을 고려해야 하는가?
//        if (token.isEmpty()) {
//            throw new TokenEmptyException();
//        }

        // RefreshToken 만료
        // exception으로 로그인 페이지로 간다
        if (!jwtTokenProvider.validateToken(token, REFRESH)) {
            throw new RefreshTokenExpiredException("RefreshToken이 만료되었습니다!");
        }

        String id = jwtTokenProvider.getSubject(token, REFRESH);
        log.info("interceptor id : {}", id);
        String accessToken = jwtTokenProvider.createAccessToken(id, null);
        request.setAttribute("id", id);
        request.setAttribute("newToken", accessToken);
        return true;
    }



}
