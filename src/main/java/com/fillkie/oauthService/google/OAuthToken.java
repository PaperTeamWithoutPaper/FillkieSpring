package com.fillkie.oauthService.google;

import lombok.Getter;

@Getter
public class OAuthToken {

  private String accessToken;
  private Long expiryDate;
  private String idToken;
  private String refreshToken;
  private String scope;
  private String tokenType;
  private String roodDir;

  public OAuthToken() {
  }

  public OAuthToken(String accessToken, Long expiresIn, String idToken, String refreshToken,
      String scope, String tokenType, String roodDir) {
    this.accessToken = accessToken;
    this.expiryDate = expiresIn;
    this.idToken = idToken;
    this.refreshToken = refreshToken;
    this.scope = scope;
    this.tokenType = tokenType;
    this.roodDir = roodDir;
  }

  @Override
  public String toString() {
    return "accessToken : " + accessToken + "\nexpiresIn : " + expiryDate + "\nidToken : " + idToken + "\nrefreshToken : " + refreshToken + "\nscope : " + scope + "\ntokenType" +tokenType;
  }
}