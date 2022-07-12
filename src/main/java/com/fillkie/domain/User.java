package com.fillkie.domain;

import com.fillkie.domain.teamDomain.Team;
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
  private String accessToken;
  private Set<Team> teams;


  @Builder
  public User(String email, String name, String accessToken, Set<Team> teams) {
    this.email = email;
    this.name = name;
    this.accessToken = accessToken;
    this.teams = teams;
  }

  @Override
  public String toString() {
    return id + "\n" + email + "\n" + name + "\n" + accessToken + "\n";
  }
}
