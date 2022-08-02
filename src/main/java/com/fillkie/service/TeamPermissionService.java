package com.fillkie.service;

import com.fillkie.controller.requestDto.UpdateTeamGroupPermissionReqDto;
import com.fillkie.controller.responseDto.PermissionGroupUsersDto;
import com.fillkie.controller.responseDto.PermissionGroupsResDto;
import com.fillkie.controller.responseDto.PermissionsResDto;
import com.fillkie.domain.group.Group;
import com.fillkie.domain.group.GroupPermission;
import com.fillkie.domain.group.GroupUser;
import com.fillkie.domain.user.User;
import com.fillkie.repository.GroupPermissionRepository;
import com.fillkie.repository.GroupRepository;
import com.fillkie.repository.GroupUserRepository;
import com.fillkie.repository.UserRepository;
import com.fillkie.security.config.CustomConfig;
import com.fillkie.security.permission.TeamPermission;
import com.fillkie.security.permission.factory.TeamPermissionFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamPermissionService {

    private final GroupPermissionRepository groupPermissionRepository;
    private final GroupUserRepository groupUserRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    private final AnnotationConfigApplicationContext factory
        = new AnnotationConfigApplicationContext(CustomConfig.class);
    private TeamPermission teamPermission;

    @PostConstruct
    public void initialize(){
        TeamPermissionFactory teamPermissionFactory = (TeamPermissionFactory) factory.getBean("teamPermissionInitializer");
        teamPermission = teamPermissionFactory.getTeamPermission();
    }

    /**
     * readGroupsTeam Groups 반환
     */
    public List<PermissionGroupsResDto> getPermissionGroups(String teamId){
        List<Group> groups = groupRepository.findByTeamId(teamId);
        List<PermissionGroupsResDto> list = new ArrayList<>();
        for(int i = 0 ; i < groups.size() ; i++){
            list.add(new PermissionGroupsResDto(groups.get(i).getId(), groups.get(i).getName()));
        }
        return list;
    }

    /**
     * Group의 permission 반환
     */
    public PermissionsResDto getPermissions(String groupId){
        GroupPermission groupPermission = groupPermissionRepository.findByGroupId(groupId)
            .orElseThrow(RuntimeException::new);
        return new PermissionsResDto(groupPermission.getPermission());
    }

    /**
     * Group에 속한 Users 반환
     */
    public List<PermissionGroupUsersDto> getPermissionGroupUsers(String teamId, String groupId){
        List<GroupUser> groupUsers = groupUserRepository.findByTeamIdAndGroupId(teamId,
            groupId);
        List<PermissionGroupUsersDto> userList = new ArrayList<>();
        for(int i = 0 ; i < groupUsers.size() ; i++){
            User user = userRepository.findById(groupUsers.get(i).getUserId()).orElseThrow(RuntimeException::new);
            userList.add(new PermissionGroupUsersDto(user.getId(), user.getName()));
        }
        return userList;
    }


    /**
     * Group의 Permission과 User을 update
     */
    @Transactional
    public void updateTeamPermission(String teamId, List<UpdateTeamGroupPermissionReqDto> updateTeamGroupPermissionReqDtos){
        for(UpdateTeamGroupPermissionReqDto updates : updateTeamGroupPermissionReqDtos){
            GroupPermission groupPermission = groupPermissionRepository.findByGroupId(
                updates.getGroupId()).orElseThrow(RuntimeException::new);
            groupPermission.setPermission(updates.getPermission());
            groupPermissionRepository.save(groupPermission);
            for(String userId : updates.getUserIds()){
                GroupUser groupUser = groupUserRepository.findByUserIdAndTeamId(userId, teamId).orElseThrow(RuntimeException::new);
                groupUser.setGroupId(updates.getGroupId());
                groupUserRepository.save(groupUser);
            }
        }
    }

    /**
     * Team에 대한 Group 생성하기 - 생성 시 권한은 없다
     */
    @Transactional
    public void createTeamGroup(String teamId, String groupName){
        Group group = saveGroup(groupName, teamId);
        GroupPermission groupPermission = saveGroupPermission(teamId, group.getId());
    }

    // TeamService와 다르게 모든 권한을 무효처리 한다.
    private GroupPermission saveGroupPermission(String teamId, String groupId){
        // json을 생성한 후 매핑하여 빈으로 만들어야 한다.
        List<Integer> permissionList = new ArrayList<>();
        for(int i = 0 ; i < 6 ; i++){
            permissionList.add(i);
        }
        GroupPermission groupPermission = GroupPermission.builder()
            .teamId(teamId)
            .groupId(groupId)
            .permission(new HashMap<>())
            .build();
        groupPermission.declinePermission(permissionList);
        groupPermissionRepository.insert(groupPermission);
        return groupPermission;
    }

    private Group saveGroup(String groupName, String teamId){
        Group group = Group.builder()
            .name(groupName)
            .teamId(teamId)
            .build();

        return groupRepository.insert(group);
    }


    //----------------------------------------- Permission 예외 처리-------------------------------------------

    /**
     * UpdatePermissionInterceptor : 팀의 Group, User에 대한 권한 update 권한 인가
     */
    public boolean CheckUpdatePermission(String userId, String teamId){
        log.info("checkUpdatePermission userId : {}", userId);
        log.info("checkUpdatePermission teamId : {}", teamId);
        GroupUser groupUser = groupUserRepository.findByUserIdAndTeamId(userId, teamId).orElseThrow(RuntimeException::new);
        GroupPermission groupPermission = groupPermissionRepository.findByGroupId(groupUser.getGroupId()).orElseThrow(RuntimeException::new);
        if(groupPermission.getPermission().get(teamPermission.UPDATE_GROUP_USER_PERMISSION)){
            return true;
        }else{
            return false;
        }
    }
}
