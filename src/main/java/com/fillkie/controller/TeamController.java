package com.fillkie.controller;

import com.fillkie.controller.requestDto.CreateTeamDto;
import com.fillkie.controller.response.DefaultResponse;
import com.fillkie.controller.response.ResponseFail;
import com.fillkie.controller.response.ResponseSuccess;
import com.fillkie.controller.responseDto.InviteTeamDto;
import com.fillkie.service.TeamService;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        log.info("createTeam userId : {}", userId);
        String teamId = teamService.saveTeam(createTeamDto, userId);
        log.info("createTeam teamId : {}", teamId);


        if(teamId == null){
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseFail(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "팀 생성 실패!"));
        }else{
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseSuccess<String>(true, HttpStatus.OK.value(), "팀 생성 성공!", teamId));
        }

    }

    @GetMapping("invite")
    public ResponseEntity<? extends DefaultResponse> inviteTeam(@RequestParam("teamId") String teamId){
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("[-]", "");

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseSuccess<InviteTeamDto>(true, HttpStatus.OK.value(), "URL 받아랑!", new InviteTeamDto(uuid)));
    }

//    @PostMapping("invite/accept/*")
//    public ResponseEntity<? extends DefaultResponse> acceptInviteTeam(){
//
//    }

    @GetMapping("test")
    public ResponseEntity<String> testResponseBody(){
        String str = "\"test\":\"sexy\"";
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(str);

    }

}
