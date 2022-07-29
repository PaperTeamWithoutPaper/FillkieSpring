package com.fillkie.security.config;

import com.fillkie.security.config.interceptor.BearerAuthInterceptor;
import com.fillkie.security.config.interceptor.RefreshAuthInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Interceptor 등록
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class AppConfig implements WebMvcConfigurer {

  private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

  private final BearerAuthInterceptor bearerAuthInterceptor;

  private final RefreshAuthInterceptor refreshAuthInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    logger.info(">>> 인터셉터 등록");
    registry.addInterceptor(refreshAuthInterceptor)
        .order(0)
        // /**로 변경해야 한다.
        .addPathPatterns("/user/refresh/**");
//        .excludePathPatterns("/user/oauth/**");
//                .addPathPatterns("/api/booking/{bookingId}")
//                .addPathPatterns("/api/rooms/{userId}/wish/{roomId}");
    registry.addInterceptor(bearerAuthInterceptor)
        .order(1)
        .addPathPatterns("/**")
        .excludePathPatterns("/user/oauth/**", "/user/test");
  }
}
