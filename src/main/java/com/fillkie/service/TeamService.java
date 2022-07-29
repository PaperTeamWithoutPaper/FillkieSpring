package com.fillkie.service;

import com.fillkie.controller.requestDto.CreateTeamReqDto;
import com.fillkie.controller.responseDto.InviteTeamDetail;
import com.fillkie.controller.responseDto.PermissionGroupUsersDto;
import com.fillkie.controller.responseDto.PermissionGroupsResDto;
import com.fillkie.controller.responseDto.PermissionsResDto;
import com.fillkie.domain.group.Group;
import com.fillkie.domain.group.GroupPermission;
import com.fillkie.domain.group.GroupUser;
import com.fillkie.domain.UserTeam;
import com.fillkie.domain.team.Team;
import com.fillkie.domain.team.TeamInvite;
import com.fillkie.domain.user.User;
import com.fillkie.repository.GroupPermissionRepository;
import com.fillkie.repository.GroupRepository;
import com.fillkie.repository.GroupUserRepository;
import com.fillkie.repository.UserRepository;
import com.fillkie.repository.UserTeamRepository;
import com.fillkie.repository.team.TeamInviteRepository;
import com.fillkie.repository.team.TeamRepository;
import com.fillkie.service.dto.TeamDetailDto;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TeamService {

    private final TeamRepository teamRepository;
    private final GroupRepository groupRepository;
    private final GroupUserRepository groupUserRepository;
    private final TeamInviteRepository teamInviteRepository;
    private final UserTeamRepository userTeamRepository;
    private final UserRepository userRepository;
    private final GroupPermissionRepository groupPermissionRepository;

    /**
     * team 저장 : userTeamId 반환
     */
    // default image 필요하다.
    @Transactional
    public String saveTeam(CreateTeamReqDto createTeamDto, String userId){
        String teamName = createTeamDto.getTeamName();

        Team team = Team.builder()
            .name(teamName)
            .image(null)
            .ownerId(userId)
            .build();
        team = teamRepository.insert(team);

        UserTeam userTeam = saveUserTeam(userId, team.getId(), teamName);

//        team.addUserTeamId(userTeam.getId());
        team = teamRepository.save(team);

        Group professor = saveGroup("professor", team.getId());
        List<Integer> permissionList;
        Group doctor = saveGroup("doctor", team.getId());
        permissionList = new ArrayList<>();
        for(int i = 7 ; i < 10 ; i++){
            permissionList.add(i);
        }
        GroupPermission doctorGroupPermission = saveGroupPermission(team.getId(), doctor.getId());
        declineGroupPermission(doctorGroupPermission, permissionList);
        Group master = saveGroup("master", team.getId());
        permissionList = new ArrayList<>();
        for(int i = 5 ; i < 10 ; i++){
            permissionList.add(i);
        }
        GroupPermission masterGroupPermission = saveGroupPermission(team.getId(), master.getId());
        declineGroupPermission(masterGroupPermission, permissionList);
        Group intern = saveGroup("intern", team.getId());
        permissionList = new ArrayList<>();
        for(int i = 3 ; i < 10 ; i++){
            permissionList.add(i);
        }
        GroupPermission internGroupPermission = saveGroupPermission(team.getId(), intern.getId());
        declineGroupPermission(internGroupPermission, permissionList);

        GroupUser groupUser = saveGroupUser(team.getId(), professor.getId(), userId);

        return userTeam.getId();
    }
    private GroupPermission declineGroupPermission(GroupPermission groupPermission, List<Integer> declinePermissionList){
        groupPermission.declinePermission(declinePermissionList);
        return groupPermissionRepository.save(groupPermission);
    }
    private GroupPermission acceptGroupPermission(GroupPermission groupPermission, List<Integer> acceptPermissionList){
        groupPermission.acceptPermission(acceptPermissionList);
        return groupPermissionRepository.save(groupPermission);
    }

    private GroupPermission saveGroupPermission(String teamId, String groupId){
        // json을 생성한 후 매핑하여 빈으로 만들어야 한다.
        List<Integer> permissionList = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i++){
            permissionList.add(i);
        }
        GroupPermission groupPermission = GroupPermission.builder()
            .teamId(teamId)
            .groupId(groupId)
            .permission(new HashMap<>())
            .build();
        groupPermission.acceptPermission(permissionList);
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

    private GroupUser saveGroupUser(String teamId, String groupId, String userId){
        GroupUser groupUser = GroupUser.builder()
            .teamId(teamId)
            .groupId(groupId)
            .userId(userId)
            .build();
        return groupUserRepository.insert(groupUser);
    }

    private UserTeam saveUserTeam(String userId, String teamId, String teamName){
        UserTeam userTeam = UserTeam.builder()
            .userId(userId)
            .teamId(teamId)
            .teamName(teamName)
            .build();
        return userTeamRepository.insert(userTeam);
    }

    // 예외 처리 필요
    private Team getTeam(String teamId){
        Team team = teamRepository.findById(teamId).orElseThrow(RuntimeException::new);
        return team;
    }

    // 예외 처리 필요
//    private List<Team> getTeamList(List<String> teamIds){
//        List<Team> teams = new ArrayList<>();
//        for(String teamId : teamIds){
//            Team team = teamRepository.findById(teamId).orElseThrow(RuntimeException::new);
//            teams.add(team);
//        }
//        return teams;
//    }

    @Transactional
    public String inviteTeam(String teamId, String url){

        Long expiryDate = getCurrentTimeMillis() + 1800000;

        // 초대 url 저장
        TeamInvite teamInvite = TeamInvite.builder()
            .teamId(teamId)
            .url(url)
            .expiryDate(expiryDate)
            .build();
        teamInvite = teamInviteRepository.insert(teamInvite);

        return teamInvite.getId();
    }

    /**
     * 팀 초대 수락 페이지에 대한 팀 정보
     */
    public InviteTeamDetail getInviteTeamDetail(String userId, String url, TeamInvite teamInvite, Team team){
        return new InviteTeamDetail(team.getName());
    }

    /**
     * Url Validation
     */
    // 예외처리로 팀이 앖거나 url이 만료되었다고 한다.
    public InviteTeamDetail validateUrl(String userId, String url){
        TeamInvite teamInvite = teamInviteRepository.findByUrl(url).orElseThrow(RuntimeException::new);
        Long expiryDate = teamInvite.getExpiryDate();
        Long accessDate = getCurrentTimeMillis();

        // 예외 처리 필요
        // 초대 만료 시간 초과 경우
        if(expiryDate - accessDate < 0){
            log.info("TeamSevice AcceptInvite Expired url : {}", url);
            return null;
        }

        Team team = teamRepository.findById(teamInvite.getTeamId()).orElseThrow(RuntimeException::new);

        // 예외 처리 필요
        // 이미 팀에 가입된 사람인 경우
        if(isJoinedUserTeam(userId, team.getId())){
            log.info("이미 팀에 가입된 사람입니다. teamId : {}, urlId : {}", team.getId(), teamInvite.getId());
           return null;
        }

        return getInviteTeamDetail(userId, url, teamInvite, team);
    }

    private boolean isJoinedUserTeam(String userId, String teamId){
        Optional<UserTeam> userTeams = userTeamRepository.findByUserIdAndTeamId(userId, teamId);
        return userTeams.isPresent();
    }

    /**
     * 초대 링크 수락 시 실행 : userTeamId 반환
     */
    // 각 객체에 대해 삭제되었을 경우의 예외를 생성해야 한다.
    // 팀이 삭제되었는데 url이 유효한 경우 예외 처리 해야 한다.
    @Transactional
    public String acceptInviteTeam(String userId, String url) throws ParseException {
        // TeamInvite 조회
        TeamInvite teamInvite = teamInviteRepository.findByUrl(url).orElseThrow(RuntimeException::new);

        // 수락을 누를 시 이미 validation이 된 것으로 판단
//        if(!validateUrl(userId, url, teamInvite)){
//            return null;
//        }

        // Team 조회
        Team team = teamRepository.findById(teamInvite.getTeamId()).orElseThrow(RuntimeException::new);

        // Group "intern"에 user 추가
        Group group = groupRepository.findByNameAndTeamId("intern", team.getId()).orElseThrow(RuntimeException::new);
        groupRepository.save(group);
        log.info("TeamSevice group intern 추가 성공 : {}", true);

        // GroupUser 생성
        GroupUser groupUser = GroupUser.builder()
            .teamId(team.getId())
            .groupId(group.getId())
            .userId(userId)
            .build();
        groupUserRepository.insert(groupUser);
        log.info("TeamSevice groupuser 생성 성공 : {}", true);

        // UserTeam 생성
        UserTeam userTeam = UserTeam.builder()
            .userId(userId)
            .teamId(team.getId())
            .teamName(team.getName())
            .build();
        userTeamRepository.insert(userTeam);

        // Team userTeamId 추가
//        team.addUserTeamId(userTeam.getId());
        teamRepository.save(team);

        return userTeam.getId();
    }

//    /**
//     * user의 팀 리스트 반환 (하나의 트랜잭션으로 해결 가능)
//     */
//    public List<String> getTeamIdList(String userId){
//        Optional<Team> teamList = teamRepository.findById(userId);
//        List<String> teamIdList = new ArrayList<>();
//        teamList.stream().forEach(team -> teamIdList.add(team.getId()));
//        return teamIdList;
//    }

    /**
     * user의 팀들 이름 반환
     */
//    public List<String> getTeamNameList(List<String> teamIds){
//        List<Team> teamList = getTeamList(teamIds);
//        List<String> teamNameList = new ArrayList<>();
//        teamList.stream().forEach(team -> teamNameList.add(team.getName()));
//        return teamNameList;
//    }

    /**
     * team의 이름, 인원수 반환
     */
    public TeamDetailDto getTeamDetail(String teamId){
        return new TeamDetailDto(getTeamName(teamId), getTeamHeadcount(teamId));
    }

    private Long getTeamHeadcount(String teamId){
        int size = userTeamRepository.findByTeamId(teamId).size();
        return Long.parseLong(String.valueOf(size));
    }

    private String getTeamName(String teamId){
        Team team = getTeam(teamId);
        return team.getName();
    }

    /**
     * 현재 시간 String 반환
     */
    private Long getCurrentTimeMillis(){
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        Calendar now = Calendar.getInstance();
//        return dateFormat.format(now.getTime());
        return now.getTimeInMillis();
    }

    /**
     * 시간 Date 반환
     */
    private Date parseDateTime(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        return dateFormat.parse(date);
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

    @Transactional
    public void updateTeamPermission(String teamId){

    }

}
