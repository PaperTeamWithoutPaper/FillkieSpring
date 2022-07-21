package com.fillkie.controller;


import com.fillkie.controller.response.DefaultResponse;
import com.fillkie.controller.response.ResponseSuccess;
import com.fillkie.controller.response.TokenResponse;
import com.fillkie.controller.responseDto.RefreshTokenResDto;
import com.fillkie.service.UserService;
import com.fillkie.service.dto.AccessRefreshDto;
import com.fillkie.service.dto.UserProfileDto;
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
    AccessRefreshDto accessRefreshDto = userService.oauthLogin(code);// access_token 발급 및 검증 실행
    HttpHeaders headers = new HttpHeaders();
    String accessToken = "bearer " + accessRefreshDto.getAccessToken();
    String refreshToken = "bearer " + accessRefreshDto.getRefreshToken();
    String redirect_url = "https://fillkie.com/loginapi?access=" + accessToken + "&refresh=" + refreshToken;
    response.sendRedirect(redirect_url);
  }

  @GetMapping("refresh")
  public ResponseEntity<? extends DefaultResponse> checkRefreshTokenUser(HttpServletRequest request){
    String accessToken = "bearer " + (String) request.getAttribute("newToken");
    log.info("new AccessToken : {}", accessToken);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ResponseSuccess<RefreshTokenResDto>(true, HttpStatus.OK.value(), "새로운 AccessToken 발급!", new RefreshTokenResDto(accessToken)));
  }


  @GetMapping("profile")
  public ResponseEntity<? extends DefaultResponse> readProfileUser(HttpServletRequest request){
    String userId = (String) request.getAttribute("id");
    log.info("UserController profile userId : {}", userId);

    UserProfileDto userProfile = userService.getUserProfile(userId);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ResponseSuccess<UserProfileDto>(true, HttpStatus.OK.value(), "사용자 프로필!", userProfile));
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
