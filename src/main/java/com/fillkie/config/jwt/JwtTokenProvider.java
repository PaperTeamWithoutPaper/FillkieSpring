package com.fillkie.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 생성, 검증
 */
@Component
@Slf4j
public class JwtTokenProvider {

  private final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

  private String secretKey;
  private long validityInMilliseconds;

  public JwtTokenProvider(@Value("&{security.jwt.token.secret-key}") String secretKey,
      @Value("${security.jwt.token.expired-length}") long validityInMilliseconds) {
    this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    this.validityInMilliseconds = validityInMilliseconds;
  }

  // 토큰 생성
  public String createToken(String id, String email) {

    //Header 부분 설정
    Map<String, Object> headers = new HashMap<>();
    headers.put("typ", "JWT");
    headers.put("alg", "HS256");

    Claims claims = Jwts.claims().setSubject(id);
//        claims.put("id", id);

    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds); // 유효기간 계산 (지금으로부터 + 유효시간)
    logger.info("now: {}", now);
    logger.info("validity: {}", validity);

    return Jwts.builder()
        .setHeader(headers)
        .setClaims(claims) // sub 설정
        .setIssuedAt(now) // 토큰 발행 일자
        .setExpiration(validity)
        .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화방식?
        .compact();
  }

  // 토큰에서 값 추출
  public String getSubject(String token) {
    return String.valueOf(Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .getBody()
        .getSubject());
  }

  // 유효한 토큰인지 확인
  public boolean validateToken(String token) {
    Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
    return !claims.getBody().getExpiration().before(new Date());
  }
}

