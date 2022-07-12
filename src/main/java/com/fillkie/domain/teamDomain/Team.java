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
  private List<String> users = new ArrayList<>();
  private List<String> projects = new ArrayList<>();
  private String roleId;
  private List<String> groups = new ArrayList<>();

  @Builder
  public Team(String name, String userId, String roleId) {
    this.name = name;
    users.add(userId);
    this.roleId =roleId;
  }

  public void addUser(String userId){
    users.add(userId);
  }

  public void addProjects(String projectId){
    projects.add(projectId);
  }

  public void addGroups(String groupId){
    groups.add(groupId);
  }

}
