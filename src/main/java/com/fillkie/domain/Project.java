package com.fillkie.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "project")
@Getter
public class Project {

  @Id
  private String id;
  private String name;
  private String dir;
  private Boolean expired;
  private String ownerId;
  private String teamId;

  @Builder
  public Project(String name, String dir, Boolean expired, String ownerId, String teamId) {
    this.name = name;
    this.dir = dir;
    this.expired = expired;
    this.ownerId = ownerId;
    this.teamId = teamId;
  }


}
