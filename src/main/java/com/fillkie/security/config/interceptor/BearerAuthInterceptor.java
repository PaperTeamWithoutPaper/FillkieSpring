package com.fillkie.security.config.interceptor;

import com.fillkie.security.advice.exception.AccessTokenExpiredException;
import com.fillkie.security.advice.exception.TokenEmptyException;
import com.fillkie.security.config.auth.AuthorizationExtractor;
import com.fillkie.security.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT 검증 Interceptor
 */
// 현재 refreshToken은 주석처리 한 상황
@Component
@RequiredArgsConstructor
@Slf4j
public class BearerAuthInterceptor implements HandlerInterceptor {

  private static final Logger logger = LoggerFactory.getLogger(BearerAuthInterceptor.class);

  private static final String AUTHORIZATION = "Authorization";
  private final AuthorizationExtractor authorizationExtractor;
  private final JwtTokenProvider jwtTokenProvider;
  private final String ACCESS = "Access";


  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) {
    logger.info(">>> interceptor.preHandle 호출");
    logger.info(">>> interceptor method : {}", request.getMethod());
    logger.info(">>> interceptor 호출 url : {}", request.getRequestURI());
    logger.info(">>> interceptor ip1 url : {}", request.getRemoteAddr());
    logger.info(">>> interceptor ip2 url : {}", request.getRemoteHost());
    String token = authorizationExtractor.extract(request, "Bearer", AUTHORIZATION);

    // 토큰이 없으므로 로그인하여 access, refresh token 발급
    // 가려던 주소로 리다이렉트
    if (token.isEmpty()) {
      throw new TokenEmptyException("토큰이 없습니다!");
    }

    // 시간 만료로 refresh token 요청 응답
    if (!jwtTokenProvider.validateToken(token, ACCESS)) {
      throw new AccessTokenExpiredException("AccessToken이 만료되었습니다!");
    }

    String id = jwtTokenProvider.getSubject(token, ACCESS);
    log.info(">>> interceptor userId : {}", id);
    request.setAttribute("id", id);

    // !!!Refresh Token으로 재발급?
//    if (!jwtTokenProvider.validateToken(token)) {
//      throw new IllegalArgumentException("유효하지 않은 토큰");
//    }
//
//    String id = jwtTokenProvider.getSubject(token);
//    log.info("interceptor id : {}", id);
//    request.setAttribute("id", id);
    return true;
  }
}
