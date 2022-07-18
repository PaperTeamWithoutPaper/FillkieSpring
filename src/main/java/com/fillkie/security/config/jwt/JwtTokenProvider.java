package com.fillkie.security.config.jwt;

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

  private String accessKey;
  private String refreshKey;
  private long validityInMilliseconds;

  public JwtTokenProvider(@Value("${security.jwt.token.secret-key.access}") String accessKey, @Value("${security.jwt.token.secret-key.refresh}") String refreshKey,
      @Value("${security.jwt.token.expired-length}") long validityInMilliseconds) {
    this.accessKey = Base64.getEncoder().encodeToString(accessKey.getBytes());
    this.refreshKey = Base64.getEncoder().encodeToString(refreshKey.getBytes());
    this.validityInMilliseconds = validityInMilliseconds;
  }

  // Access Token 생성
  public String createAccessToken(String id, String email) {

    //Header 부분 설정
    Map<String, Object> headers = new HashMap<>();
    headers.put("typ", "JWT");
    headers.put("alg", "HS256");

    Claims claims = Jwts.claims().setSubject(id);
//        claims.put("id", id);

    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds * 2); // 유효기간 계산 (지금으로부터 + 유효시간)
    logger.info("now: {}", now);
    logger.info("validity: {}", validity);

    return Jwts.builder()
        .setHeader(headers)
        .setClaims(claims) // sub 설정
        .setIssuedAt(now) // 토큰 발행 일자
        .setExpiration(validity)
        .signWith(SignatureAlgorithm.HS256, accessKey) // 암호화방식?
        .compact();
  }

  // Refresh Token 생성
  public String createRefreshToken(String id, String email) {

    //Header 부분 설정
    Map<String, Object> headers = new HashMap<>();
    headers.put("typ", "JWT");
    headers.put("alg", "HS256");

    Claims claims = Jwts.claims().setSubject(id);
//        claims.put("id", id);

    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds * 24 * 14); // 유효기간 계산 (지금으로부터 + 유효시간)
    logger.info("now: {}", now);
    logger.info("validity: {}", validity);

    return Jwts.builder()
        .setHeader(headers)
        .setClaims(claims) // sub 설정
        .setIssuedAt(now) // 토큰 발행 일자
        .setExpiration(validity)
        .signWith(SignatureAlgorithm.HS256, refreshKey) // 암호화방식?
        .compact();
  }

  // 토큰에서 값 추출
  public String getSubject(String token, String type) {
    String key;
    if(type.equals("Access")){
      key = accessKey;
    }else{
      key = refreshKey;
    }
    return String.valueOf(Jwts.parser()
        .setSigningKey(accessKey)
        .parseClaimsJws(token)
        .getBody()
        .getSubject());
  }

  // 유효한 토큰인지 확인
  public boolean validateToken(String token, String type) {
    String key;
    if(type.equals("Access")){
      key = accessKey;
    }else{
      key = refreshKey;
    }
    Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    return !claims.getBody().getExpiration().before(new Date());
  }

}

