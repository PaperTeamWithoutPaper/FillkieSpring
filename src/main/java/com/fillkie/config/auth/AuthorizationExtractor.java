package com.fillkie.config.auth;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Component
@Slf4j
public class AuthorizationExtractor {

  public static final String AUTHORIZATION = "Authorization";
  public static final String ACCESS_TOKEN_TYPE = AuthorizationExtractor.class.getSimpleName();

  public String extract(HttpServletRequest request, String type) {
    request.getHeaderNames().asIterator()
        .forEachRemaining(header -> log.info("Extractor header name : {}, value : {}", header, request.getHeaders(header)));
    Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
    while (headers.hasMoreElements()) {
      String value = headers.nextElement();
      if (value.toLowerCase().startsWith(type.toLowerCase())) {
        return value.substring(type.length()).trim();
      }
    }
    return Strings.EMPTY;
  }
}