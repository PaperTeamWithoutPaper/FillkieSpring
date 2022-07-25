package com.fillkie.domain.team;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "team")
@Getter
public class Team {

  @Id
  private String id;
  private String name;
  private String image;
  private String ownerId;

  @Builder
  public Team(String name, String image, String ownerId) {
    this.name = name;
    this.image = image;
    this.ownerId = ownerId;
  }

//  public void addUserTeamId(String userTeamId){
//    userTeamIds.add(userTeamId);
//  }

//  public void addProjects(String projectId){
//    projects.add(projectId);
//  }


}
