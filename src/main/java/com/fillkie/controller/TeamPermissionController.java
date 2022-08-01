package com.fillkie.controller;

import com.fillkie.controller.requestDto.CreateTeamGroupReqDto;
import com.fillkie.controller.requestDto.UpdateTeamGroupPermissionReqDto;
import com.fillkie.controller.response.DefaultResponse;
import com.fillkie.controller.response.ResponseSuccess;
import com.fillkie.controller.responseDto.PermissionGroupUsersDto;
import com.fillkie.controller.responseDto.PermissionGroupsResDto;
import com.fillkie.controller.responseDto.PermissionsResDto;
import com.fillkie.service.TeamPermissionService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("permission")
@RequiredArgsConstructor
public class TeamPermissionController {

    private final TeamPermissionService teamPermissionService;


    /**
     * 팀에 존재하는 Group 조회
     */
    @GetMapping("groups/{teamId}")
    public ResponseEntity<? extends DefaultResponse> readGroupsPermission(@PathVariable("teamId") String teamId){
        List<PermissionGroupsResDto> groups = teamPermissionService.getPermissionGroups(teamId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ResponseSuccess<List<PermissionGroupsResDto>>(true, HttpStatus.OK.value(), "Group들!", groups));
    }

    /**
     * 팀의 각 Group에 대한 권한 상태 조회
     */
    @GetMapping("{groupId}/{teamId}")
    public ResponseEntity<? extends DefaultResponse> readGroupPermissionPermission(@PathVariable("groupId") String groupId, @PathVariable("teamId") String teamId){
        PermissionsResDto permissions = teamPermissionService.getPermissions(groupId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ResponseSuccess<PermissionsResDto>(true, HttpStatus.OK.value(), "권한들!", permissions));
    }

    /**
     * 팀의 각 Group에 속한 User 조회
     */
    @GetMapping("users/{groupId}/{teamId}")
    public ResponseEntity<? extends DefaultResponse> readGroupUsersPermission(@PathVariable("groupId") String groupId, @PathVariable("teamId") String teamId){
        List<PermissionGroupUsersDto> usersList = teamPermissionService.getPermissionGroupUsers(
            teamId, groupId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ResponseSuccess<List<PermissionGroupUsersDto>>(true, HttpStatus.OK.value(), "Group에 속한 Users!", usersList));
    }

    /**
     * 팀의 각 Group의 Permission, User 업데이트
     */
    @PutMapping("update/{teamId}")
    public ResponseEntity<? extends DefaultResponse> updateTeamGroupPermission(@PathVariable("teamId") String teamId, @RequestBody @Valid List<UpdateTeamGroupPermissionReqDto> updateTeamGroupPermissionReqDtos){
        teamPermissionService.updateTeamPermission(teamId, updateTeamGroupPermissionReqDtos);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ResponseSuccess<String>(true, HttpStatus.OK.value(), "Permission 갱신 성공!", "성공!"));
    }

    /**
     * 팀에 Group 생성
     */
    @PostMapping("group/create")
    public ResponseEntity<? extends DefaultResponse> createTeamGroup(@RequestBody @Valid
        CreateTeamGroupReqDto createTeamGroupReqDto){
        teamPermissionService.createTeamGroup(createTeamGroupReqDto.getTeamId(), createTeamGroupReqDto.getGroupName());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ResponseSuccess<String>(true, HttpStatus.OK.value(), "Group 생성 성공!", "성공!"));
    }

    /**
     * 팀에 그룹 생성
     */
    // 초기에 그룹에는 User이 아무도 속하지 않은 상태로 만든다
//    @PostMapping("create/group")
//    public ResponseEntity<? extends DefaultResponse> createGroupPermission(){
//
//    }

}
