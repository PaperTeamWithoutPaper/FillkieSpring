package com.fillkie.domain;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@Getter
public class User {

  @Id
  private String id;
  private String email;
  private String name;
  private String image;
  private Boolean expired;
  private String accessToken;
  private String refreshToken;
  private List<String> userTeamIds;


  @Builder
  public User(String email, String name, String image, Boolean expired, String accessToken, String refreshToken, List<String> userTeamIds) {
    this.email = email;
    this.name = name;
    this.image = image;
    this.expired = expired;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.userTeamIds = userTeamIds;
  }

  public void addUserTeamId(String userTeamId){
    userTeamIds.add(userTeamId);
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  @Override
  public String toString() {
    return id + "\n" + email + "\n" + name + "\n" + accessToken + "\n";
  }
}
