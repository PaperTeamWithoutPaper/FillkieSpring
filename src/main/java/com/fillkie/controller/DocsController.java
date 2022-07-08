package com.fillkie.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DocsController {


  @GetMapping("user")
  public ResponseEntity<? extends Data<GetMain>> getUserInfo() {
    GetMain getMain = new GetMain();
    return new ResponseEntity(new Data<GetMain>(getMain), HttpStatus.OK);
  }

//    @GetMapping("team")

  // id 가져옴
  @GetMapping("team")
  public ResponseEntity<? extends Data<GetTeams>> getTeamList() {
    GetTeams getTeams = new GetTeams();
    return new ResponseEntity(new Data<GetTeams>(getTeams), HttpStatus.OK);
  }

  @GetMapping("project")
  public ResponseEntity<? extends Data<GetProjects>> getProjectList() {
    GetProjects getProjects = new GetProjects();
    return new ResponseEntity(new Data<GetProjects>(getProjects), HttpStatus.OK);
  }


  @GetMapping("team/{teamId}")
  public ResponseEntity<? extends Data<GetTeamName>> getTeamName(
      @PathVariable("teamId") String teamId) {

    GetTeamName getTeamName = new GetTeamName();

    return new ResponseEntity(new Data<GetTeamName>(getTeamName), HttpStatus.OK);
  }

  @PostMapping("team")
  public String saveTeam() {
    return "error : ";
  }


  @AllArgsConstructor
  static class Data<T> {

    private T Result;
  }

  class GetMain {

    String username = "‍유재원";
    String email = "sjf471@korea.ac.kr";
  }

  class GetTeams {

    ArrayList<String> teams = new ArrayList<>(Arrays.asList("###@@@@1", "###@@@@2"));
  }

  class GetProjects {

    ArrayList<String> projects = new ArrayList<>(Arrays.asList("research1, lab2"));
  }

  class GetTeamName {

    String teamId = "###@@@@1";
    String teamName = "Korea";
  }

}