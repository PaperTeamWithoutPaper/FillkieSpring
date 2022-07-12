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
  private String user;
  private String directory;
  private String teamId;


}
