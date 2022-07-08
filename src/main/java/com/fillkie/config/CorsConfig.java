package com.fillkie.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    // 내 서버가 응답할 때 json을 자바스크립트에서 처리할 수 있게 할지를 설정하는 것이다.
    // false이면 자바스크립트로 요청했을 때 응답하지 않는다.
    config.setAllowCredentials(true);
    // 어디서든지 허용해주게 하는 것이다.
    // 모든 ip에 응답을 허용하는 것이다.
    config.addAllowedOriginPattern("*");
    // 모든 헤더 허용한다.
    // 모든 헤더에 응답을 허용한는 것이다.
    config.addAllowedHeader("*");
    // 모든 http 메소드 허용한다.
    // 모든 post, get, put, delete, patch 요청을 허용하는 것이다.
    config.addAllowedMethod("*");
    // 클라이언트가 응답의 모든 헤더를 읽을 수 있게 한다.
    config.addExposedHeader("*");
    // "/api/**"로 들어오는 것은 이 config 설정을 따르게 하라.
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }
}
