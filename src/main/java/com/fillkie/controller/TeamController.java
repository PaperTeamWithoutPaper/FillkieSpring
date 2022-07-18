package com.fillkie.controller;

import com.fillkie.controller.requestDto.AcceptInviteTeamReqDto;
import com.fillkie.controller.requestDto.CreateTeamReqDto;
import com.fillkie.controller.response.DefaultResponse;
import com.fillkie.controller.response.ResponseFail;
import com.fillkie.controller.response.ResponseSuccess;
import com.fillkie.controller.responseDto.InviteTeamResDto;
import com.fillkie.controller.responseDto.TeamListResDto;
import com.fillkie.controller.responseDto.ValidateUrlResDto;
import com.fillkie.service.TeamService;
import com.fillkie.service.UserService;
import com.fillkie.service.dto.TeamDetailDto;
import java.text.ParseException;
import java.util.List;
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
    private final UserService userService;

    @PostMapping("create")
    public ResponseEntity<? extends DefaultResponse> createTeam(@RequestBody @Valid CreateTeamReqDto createTeamDto, HttpServletRequest request){
        String userId = (String) request.getAttribute("id");
        log.info("createTeam userId : {}", userId);
        String userTeamId = teamService.saveTeam(createTeamDto, userId);
        log.info("createTeam userTeamId : {}", userTeamId);
        userService.addUserTeam(userId, userTeamId);


        if(userTeamId == null){
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseFail(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "팀 생성 실패!"));
        }else{
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseSuccess<String>(true, HttpStatus.OK.value(), "팀 생성 성공!", userId));
        }

    }

    @GetMapping("invite")
    public ResponseEntity<? extends DefaultResponse> inviteTeam(@RequestParam("teamId") String teamId){
        String url = UUID.randomUUID().toString();
        url = url.replaceAll("[-]", "").trim();

        teamService.inviteTeam(teamId, url);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseSuccess<InviteTeamResDto>(true, HttpStatus.OK.value(), "URL 받아랑!", new InviteTeamResDto(url)));
    }

    @GetMapping("invite/validation")
    public ResponseEntity<? extends DefaultResponse> validateUrl(@RequestBody @Valid AcceptInviteTeamReqDto acceptInviteTeamReqDto, HttpServletRequest request){
        String userId = (String) request.getAttribute("id");
        log.info("TeamController validateUrl url : {}", acceptInviteTeamReqDto.getUrl());
        ValidateUrlResDto validateUrlResDto = teamService.validateUrl(userId, acceptInviteTeamReqDto.getUrl());
        log.info("TeamController validateUrl teamName : {}", validateUrlResDto.getTeamName());

        // 예외 처리로 해결한다.
        if(validateUrlResDto == null){
            return ResponseEntity
                .status(HttpStatus.REQUEST_TIMEOUT)
                .body(new ResponseFail(false, HttpStatus.REQUEST_TIMEOUT.value(), "초대 시간 만료되었거나 팀이 삭제 되었거나 이미 팀에 소속되어 있습니다!"));
        }else{
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseSuccess<ValidateUrlResDto>(false, HttpStatus.OK.value(), "수락해주세요!", validateUrlResDto));
        }

    }

    @PostMapping("invite/accept")
    public ResponseEntity<? extends DefaultResponse> acceptInviteTeam(@RequestBody @Valid
        AcceptInviteTeamReqDto acceptInviteTeamReqDto, HttpServletRequest request)
        throws ParseException {
        String userId = (String) request.getAttribute("id");
        log.info("TeamController inviteAccept url : {}", acceptInviteTeamReqDto.getUrl());
        String userTeamId = teamService.acceptInviteTeam(userId, acceptInviteTeamReqDto.getUrl());
        log.info("TeamController userTeamId : {}", userTeamId);
        userService.addUserTeam(userId, userTeamId);

        if(userTeamId == null){
            return ResponseEntity
                .status(HttpStatus.GONE)
                .body(new ResponseFail(false, HttpStatus.NOT_ACCEPTABLE.value(), "시간 초과!"));
        }else{
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseSuccess<String>(true, HttpStatus.OK.value(), "팀 합류 성공!", userTeamId));
        }
    }

    @GetMapping("list")
    public ResponseEntity<? extends DefaultResponse> readListTeam(HttpServletRequest request){

        String userId = (String) request.getAttribute("id");

        List<TeamListResDto> teamList = userService.getTeamList(userId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ResponseSuccess<List<TeamListResDto>>(true, HttpStatus.OK.value(), "팀 리스트!", teamList));
    }

    @GetMapping("detail")
    public ResponseEntity<? extends DefaultResponse> readDetailTeam(@RequestParam("teamId") String teamId){

        TeamDetailDto teamDetailDto = teamService.getTeamDetail(teamId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ResponseSuccess<TeamDetailDto>(true, HttpStatus.OK.value(), "팀 명, 인원수!", teamDetailDto));
    }

}
