package com.fillkie.domain;

import org.springframework.data.annotation.Id;

public class File {

  @Id
  private String id;
  private String path;
  private String source;

}
