package com.fillkie.security.config.interceptor;

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
@Component
@RequiredArgsConstructor
@Slf4j
public class BearerAuthInterceptor implements HandlerInterceptor {

  private static final Logger logger = LoggerFactory.getLogger(BearerAuthInterceptor.class);

  private static final String AUTHORIZATION = "Authorization";
  private final AuthorizationExtractor authorizationExtractor;
  private final JwtTokenProvider jwtTokenProvider;


  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) {
    logger.info(">>> interceptor.preHandle 호출");
    logger.info(">>> interceptor method : {}", request.getMethod());
    logger.info(">>> interceptor 호출 url : {}", request.getRequestURI());
    logger.info(">>> interceptor ip1 url : {}", request.getRemoteAddr());
    logger.info(">>> interceptor ip2 url : {}", request.getRemoteHost());
    String token = authorizationExtractor.extract(request, "Bearer", AUTHORIZATION);
    if (token.isEmpty()) {
      throw new TokenEmptyException();
    }

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
