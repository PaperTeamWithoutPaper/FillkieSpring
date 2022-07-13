package com.fillkie.domain;

import com.fillkie.domain.teamDomain.Team;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "user")
@Getter
public class User {

  @Id
  private String id;
  private String email;
  private String name;
  private String image;
  private String accessToken;
  private String refreshToken;
  private List<String> teams;


  @Builder
  public User(String email, String name, String image, String accessToken, String refreshToken, List<String> teams) {
    this.email = email;
    this.name = name;
    this.image = image;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.teams = teams;
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
