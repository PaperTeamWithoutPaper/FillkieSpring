package com.fillkie.controller;

import com.fillkie.controller.response.DefaultResponse;
import com.fillkie.controller.response.ResponseSuccess;
import com.fillkie.controller.responseDto.PermissionGroupUsersDto;
import com.fillkie.controller.responseDto.PermissionGroupsResDto;
import com.fillkie.controller.responseDto.PermissionsResDto;
import com.fillkie.service.TeamService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("permission")
@RequiredArgsConstructor
public class TeamPermissionController {

    private final TeamService teamService;

    @GetMapping("groups/{teamId}")
    public ResponseEntity<? extends DefaultResponse> readGroupsTeam(@PathVariable("teamId") String teamId){
        List<PermissionGroupsResDto> groups = teamService.getPermissionGroups(teamId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ResponseSuccess<List<PermissionGroupsResDto>>(true, HttpStatus.OK.value(), "Group들!", groups));
    }

    @GetMapping("{groupId}/{teamId}")
    public ResponseEntity<? extends DefaultResponse> readGroupPermissionTeam(@PathVariable("groupId") String groupId, @PathVariable("teamId") String teamId){
        PermissionsResDto permissions = teamService.getPermissions(groupId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ResponseSuccess<PermissionsResDto>(true, HttpStatus.OK.value(), "권한들!", permissions));
    }

    @GetMapping("users/{groupId}/{teamId}")
    public ResponseEntity<? extends DefaultResponse> readGroupUsersTeam(@PathVariable("groupId") String groupId, @PathVariable("teamId") String teamId){
        List<PermissionGroupUsersDto> usersList = teamService.getPermissionGroupUsers(
            teamId, groupId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ResponseSuccess<List<PermissionGroupUsersDto>>(false, HttpStatus.OK.value(), "Group에 속한 Users!", usersList));
    }
}
