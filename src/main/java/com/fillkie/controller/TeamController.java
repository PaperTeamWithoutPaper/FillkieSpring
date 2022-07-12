package com.fillkie.controller;

import com.fillkie.controller.DocsController.Data;
import com.fillkie.controller.DocsController.GetTeams;
import com.fillkie.controller.dto.CreateTeamDto;
import com.fillkie.controller.response.DefaultResponse;
import com.fillkie.controller.response.ResponseFail;
import com.fillkie.controller.response.ResponseSuccess;
import com.fillkie.service.TeamService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
@Slf4j
public class TeamController {

    private final TeamService teamService;

    @PostMapping("create")
    public ResponseEntity<? extends DefaultResponse> createTeam(@RequestBody @Valid CreateTeamDto createTeamDto, HttpServletRequest request){
        String userId = (String) request.getAttribute("id");
        String teamId = teamService.saveTeam(createTeamDto, userId);

        if(teamId == null){
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseFail(true, HttpStatus.INTERNAL_SERVER_ERROR.value(), "팀 생성 실패!"));
        }else{
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseSuccess<String>(true, HttpStatus.OK.value(), "팀 생성 성공!", teamId));
        }


    }

}
