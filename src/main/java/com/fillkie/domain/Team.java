package com.fillkie.domain;

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

}
