package com.fillkie.domain.teamDomain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "team")
@Getter
public class Team {

  @Id
  private String id;
  private String name;
  private List<String> users;
  private List<String> projects;

  @Builder
  public Team(String name, List<String> users, List<String> projects) {
    this.name = name;
    this.users = users;
    this.projects = projects;
  }

  public void addUser(String userId){
    users.add(userId);
  }

  public void addProjects(String projectId){
    projects.add(projectId);
  }


}
