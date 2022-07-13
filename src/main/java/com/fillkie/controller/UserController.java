package com.fillkie.controller;


import com.fillkie.controller.response.TokenResponse;
import com.fillkie.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

  private final UserService userService;

  /**
   * OAuth Redirect url, Login 검증 및 인증
   *
   * @param code : authorization_code
   * @return : JWT(header)
   */
  @GetMapping("/oauth/google")
  public void oauthLogin(@RequestParam("code") String code, HttpServletResponse response)
      throws IOException {
    System.out.println("code : " + code);
    String token = userService.oauthLogin(code); // access_token 발급 및 검증 실행
    HttpHeaders headers = new HttpHeaders();
    token = "bearer " + token;
    String redirect_url = "http://localhost:3000/loginapi?token=" + token;
    response.sendRedirect(redirect_url);
  }

  @GetMapping("/test")
  public void testToken(HttpServletRequest request, HttpServletResponse response) {
    log.info("UserController testToken : {}", request.getAttribute("email"));
//        response.addHeader("Access-Control-Allow-Origin", "*");

//    log.info("secretKey : {}", secretKey);
//    log.info("expiredLength : {}", expiredLength);
//    log.info("POST_URL : {}", POST_URL);
//    log.info("GET_URL : {}", GET_URL);

    response.setHeader("sexy", "sexy");
  }
}
