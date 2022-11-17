package com.fillkie.domain.user;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "user")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @Id
  private String id;
  private String email;
  private String name;
  private String image;
  @Field("google")
  private Google google;

  @Builder
  public User(String email, String name, String image, Long expiryDate, String accessToken, String refreshToken) {
    this.email = email;
    this.name = name;
    this.image = image;
    google = Google.builder()
        .expiryDate(expiryDate)
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

//  public User(String id, String email, String name, String image, Google google) {
//    this.id = id;
//    this.email = email;
//    this.name = name;
//    this.image = image;
//    this.google = google;
//  }
//  public void addUserTeamId(String userTeamId){
//    userTeamIds.add(userTeamId);
//  }

  public void setExpiryDate(Long expiryDate){
    this.google.setExpiryDate(expiryDate);
  }
  public void setAccessToken(String accessToken) {
    this.google.setAccessToken(accessToken);
  }

  public String getAccessToken(){
    return this.google.getAccessToken();
  }

  public void setRefreshToken(String refreshToken) {
    this.google.setRefreshToken(refreshToken);
  }

//  @Override
//  public String toString() {
//    return id + "\n" + email + "\n" + name + "\n" + accessToken + "\n";
//  }
}
