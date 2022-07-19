package com.fillkie.domain.teamDomain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "team")
@Getter
public class Team {

  @Id
  private String id;
  private String name;
//  private String image;
  private List<String> userTeamIds;
  private List<String> projects;

  @Builder
  public Team(String name, List<String> userTeamIds, List<String> projects) {
    this.name = name;
    this.userTeamIds = userTeamIds;
    this.projects = projects;
  }

  public void addUserTeamId(String userTeamId){
    userTeamIds.add(userTeamId);
  }

  public void addProjects(String projectId){
    projects.add(projectId);
  }


}
