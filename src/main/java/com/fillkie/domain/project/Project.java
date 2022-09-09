package com.fillkie.domain.project;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "project")
@Getter
public class Project {

  @Id
  private String id;
  private String dir;
  private String name;
  private String ownerId;
  private String teamId;

  @Builder
  public Project(String dir, String name, String ownerId, String teamId) {
    this.dir = dir;
    this.name = name;
    this.ownerId = ownerId;
    this.teamId = teamId;
  }


}
