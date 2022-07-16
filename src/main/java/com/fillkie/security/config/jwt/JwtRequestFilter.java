package com.fillkie.security.config.jwt;


import com.fillkie.security.advice.exception.AccessTokenExpiredException;
import com.fillkie.security.advice.exception.TokenEmptyException;
import com.fillkie.security.config.auth.AuthorizationExtractor;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @Title       JWT Request Filter
 * @Author      강민우
 * @Developer   강민우
 * @Date        2022-01-04
 * @Description JWT을 검증하는 filter
 *              OncePerRequestFilter를 상속해 요청당 한번의 filter를 수행한다.
 *              EXCLUDE_URL에 있는 url은 체크하지 않는다.
 */
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private static final String AUTHORIZATION = "Authorization";


	private final JwtTokenProvider jwtTokenProvider;

	private final AuthorizationExtractor authorizationExtractor;
	
	// 포함하지 않을 url
	private static final List<String> EXCLUDE_URL =
		Collections.unmodifiableList(
			Arrays.asList(
				"/static/*",
				"/favicon.ico",
				"/user/oauth/*",
				"/user/refreshToken/*"
//				"/admin",
//				"/admin/authentication",
//				"/admin/refresh",
//				"/admin/join",
//				"/admin/join/**",
//				"/admin/loginView",
//				"/admin/login"
			));
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// jwt local storage 사용 시
		// final String token = request.getHeader("Authorization");
		
		// jwt cookie 사용 시
//		String token = Arrays.stream(request.getCookies())
//				.filter(c -> c.getName().equals("jdhToken"))
//				.findFirst() .map(Cookie::getValue)
//				.orElse(null);

		// jwt 헤더 사용 시
		String token = authorizationExtractor.extract(request, "Bearer", AUTHORIZATION);

		// 토큰이 없으므로 로그인하여 access, refresh token 발급
		// 가려던 주소로 리다이렉트
		if (token.isEmpty()) {
			throw new TokenEmptyException("토큰이 없습니다!");
		}

		// 시간 만료로 refresh token 요청 응답
		if (!jwtTokenProvider.validateToken(token)) {
			throw new AccessTokenExpiredException("AccessToken이 만료되었습니다!");
		}

		String id = jwtTokenProvider.getSubject(token);
		log.info("JwtRequestFilter userId : {}", id);
		request.setAttribute("id", id);
		
		filterChain.doFilter(request,response);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return EXCLUDE_URL.stream().anyMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()));
	}
}