package com.fillkie.oauthService.google;

import com.fillkie.domain.User;
import java.util.ArrayList;
import lombok.Data;

import java.util.HashSet;

@Data
public class GoogleUser {

  public String id;
  public String email;
  public Boolean verifiedEmail;
  public String name;
  public String givenName;
  public String familyName;
  public String picture;
  public String locale;

  public String hd;

  public GoogleUser() {
  }

  public GoogleUser(String id, String email, Boolean verifiedEmail, String name, String givenName,
      String familyName, String picture, String locale, String hd) {
    this.id = id;
    this.email = email;
    this.verifiedEmail = verifiedEmail;
    this.name = name;
    this.givenName = givenName;
    this.familyName = familyName;
    this.picture = picture;
    this.locale = locale;
    this.hd = hd;
  }

  public User toUser(String accessToken, String refreshToken) {
    return User.builder()
        .email(email)
        .name(name)
        .image(picture)
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .teams(new ArrayList<>())
        .build();
  }

}
