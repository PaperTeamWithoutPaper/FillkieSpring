package com.fillkie.service.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fillkie.oauthService.google.GoogleUser;
import com.fillkie.oauthService.google.OAuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuthService {

  private ObjectMapper objectMapper = new ObjectMapper().setPropertyNamingStrategy(
      PropertyNamingStrategy.SNAKE_CASE);;
  private final RestTemplate restTemplate;

  @Value("${oauth.google.client-id}")
  private String CLIENT_ID;

  @Value("${oauth.google.client-secret}")
  private String CLIENT_SECRET;

  @Value("${oauth.google.redirect-uri}")
  private String REDIRECT_URI;

  @Value("${oauth.google.grant-type}")
  private String GRANT_TYPE;

  @Value("${oauth.google.post-url}")
  private String POST_URL;

  @Value("${oauth.google.get-url}")
  private String GET_URL;

  /**
   * Access_Token POST 요청
   *
   * @param code : authorization_code
   * @return ResponseEntity<String>
   */
  public ResponseEntity<String> createPostRequest(String code) {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("code", code);
    params.add("client_id", CLIENT_ID);
    params.add("client_secret", CLIENT_SECRET);
    params.add("redirect_uri", REDIRECT_URI);
    params.add("grant_type", GRANT_TYPE);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/x-www-form-urlencoded");

    HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

    return restTemplate.exchange(POST_URL, HttpMethod.POST, httpEntity, String.class);
  }

  /**
   * ResponseEntity에서 Access_Token 가져오기
   *
   * @param response
   * @return OAuthToken
   */
  public OAuthToken getAccessToken(ResponseEntity<String> response) {
    OAuthToken oAuthToken = null;
    try {
      oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
      log.info("Access Token Body : {}", response.getBody());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return oAuthToken;
  }

  /**
   * UserInfo GET 요청
   *
   * @param oAuthToken : Access_Token
   * @return ResponseEntity<String>
   */
  public ResponseEntity<String> createGetRequest(OAuthToken oAuthToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + oAuthToken.getAccessToken());

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(headers);

    return restTemplate.exchange(GET_URL, HttpMethod.GET, request, String.class);
  }

  /**
   * ResponseEntity에서 UserInfo 가져오기
   *
   * @param userInfoResponse
   * @return GoogleUser
   */
  public GoogleUser getUserInfo(ResponseEntity<String> userInfoResponse) {
    GoogleUser googleUser = null;
    try {
      googleUser = objectMapper.readValue(userInfoResponse.getBody(), GoogleUser.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return googleUser;
  }
}