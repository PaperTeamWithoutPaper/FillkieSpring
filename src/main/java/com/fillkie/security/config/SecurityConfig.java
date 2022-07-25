package com.fillkie.security.config;

import com.fillkie.security.config.jwt.JwtRequestFilter;
import com.fillkie.security.handler.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CorsConfig corsConfig;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtRequestFilter jwtRequestFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
//        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        // @CrossOrigin은 인증이 없을 때 실행되고 시큐리티 필터에 필터를 등록해야 인증이 있을 때 실행된다.
        // 이걸로 이제 요청 시 시큐리티의 로그인 창이 뜨지 않는다.
        .addFilter(corsConfig.corsFilter())
        // jwt 서버이므로 폼 로그인을 하지 않은다.
        .formLogin().disable()
        // 기본적인 http 로그인 방식을 사용하지 않는 것이다.
        .httpBasic().disable()
        // formLogin().disable()로 실행되지 않는 필터를 실행되게 하려고 다시 적용한다.
        // id, pw 로 로그인을 진행하는 필터이므로 AuthenticationManger이라는 파라미터를 줘야 한다.
        // WebSecurityConfigurerAdapter 를 상속받았는데 이 메소드에 AuthenticationManager가 포함되어 있어서 그대로 사용한다.
//                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
//                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
        .authorizeRequests()
//                .mvcMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .antMatchers("/api/v1/user/**")
        .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
        .antMatchers("/api/v1/manager/**")
        .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
        .antMatchers("/api/v1/admin/**")
        .access("hasRole('ROLE_ADMIN')")
        .anyRequest().permitAll()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(jwtAuthenticationEntryPoint);
    return http.build();
  }
}
