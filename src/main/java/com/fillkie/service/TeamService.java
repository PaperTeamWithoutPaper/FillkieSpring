package com.fillkie.service;

import com.fillkie.controller.requestDto.CreateTeamReqDto;
import com.fillkie.domain.Group;
import com.fillkie.domain.GroupUser;
import com.fillkie.domain.teamDomain.Team;
import com.fillkie.domain.teamDomain.TeamInvite;
import com.fillkie.repository.GroupRepository;
import com.fillkie.repository.GroupUserRepository;
import com.fillkie.repository.team.TeamInviteRepository;
import com.fillkie.repository.team.TeamRepository;
import com.fillkie.service.dto.TeamDetailDto;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

    @Transactional
    public String saveTeam(CreateTeamReqDto createTeamDto, String userId){
        String teamName = createTeamDto.getTeamName();

        Team team = Team.builder()
            .name(teamName)
            .users(new ArrayList<>())
            .projects(new ArrayList<>())
            .build();
        team.addUser(userId);
        team = teamRepository.insert(team);

        Group professor = saveGroup("professor", team.getId(), userId, true);
        Group doctor = saveGroup("doctor", team.getId(), userId, false);
        Group master = saveGroup("master", team.getId(), userId, false);
        Group intern = saveGroup("intern", team.getId(), userId, false);

        GroupUser groupUser = saveGroupUser(team.getId(), professor.getId(), userId);

        return team.getId();

    }

    private Group saveGroup(String groupName, String teamId, String userId, boolean flag){
        Group group = Group.builder()
            .name("professor")
            .teamId(teamId)
            .users(new ArrayList<>())
            .roles(new ArrayList<>())
            .build();
        if (flag){
            group.addUser(userId);
        }
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

    // 예외 처리 필요
    private Team getTeam(String teamId){
        Team team = teamRepository.findById(teamId).orElseThrow(RuntimeException::new);
        return team;
    }

    // 예외 처리 필요
    private List<Team> getTeamList(List<String> teamIds){
        List<Team> teams = new ArrayList<>();
        for(String teamId : teamIds){
            Team team = teamRepository.findById(teamId).orElseThrow(RuntimeException::new);
            teams.add(team);
        }
        return teams;
    }

    @Transactional
    public String inviteTeam(String teamId, String url){

        String startDateTime = getCurrentTime();

        // 초대 url 저장
        TeamInvite teamInvite = TeamInvite.builder()
            .teamId(teamId)
            .url(url)
            .startDateTime(startDateTime)
            .build();
        teamInvite = teamInviteRepository.insert(teamInvite);

        return teamInvite.getId();
    }

    // expired 예외 만들어주기
    @Transactional
    public String acceptInviteTeam(String url, String userId) throws ParseException {
        TeamInvite teamInvite = teamInviteRepository.findByUrl(url).orElseThrow(RuntimeException::new);

        String startDateTime = teamInvite.getStartDateTime();
        String accessDateTime = getCurrentTime();
        Date start = parseDateTime(startDateTime);
        Date access = parseDateTime(accessDateTime);
        // 예외처리 필요
        if((access.getTime() - start.getTime()) / 1000 > 1800){
            log.info("TeamSevice AcceptInvite Expired url : {}", url);
            return null;
        }

        // Team에 user 추가
        Team team = teamRepository.findById(teamInvite.getTeamId()).orElseThrow(RuntimeException::new);
        team.addUser(userId);
        teamRepository.save(team);
        log.info("TeamSevice team user 추가 성공 : {}", true);

        // Group "intern"에 user 추가
        Group group = groupRepository.findByNameAndTeamId("intern", team.getId()).orElseThrow(RuntimeException::new);
        group.addUser(userId);
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

        return team.getId();
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
    public List<String> getTeamNameList(List<String> teamIds){
        List<Team> teamList = getTeamList(teamIds);
        List<String> teamNameList = new ArrayList<>();
        teamList.stream().forEach(team -> teamNameList.add(team.getName()));
        return teamNameList;
    }

    /**
     * team의 이름, 인원수 반환
     */
    public TeamDetailDto getTeamDetail(String teamId){
        Team team = getTeam(teamId);
        return new TeamDetailDto(team.getName(), team.getUsers().size());
    }
//
//    public int getTeamHeadcount(String teamId){
//        Team team = getTeam(teamId);
//        return team.getUsers().size();
//    }
//
//    public String getTeamName(String teamId){
//        Team team = getTeam(teamId);
//        return team.getName();
//    }

    /**
     * 현재 시간 String 반환
     */
    private String getCurrentTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        Calendar now = Calendar.getInstance();
        return dateFormat.format(now.getTime());
    }

    /**
     * 시간 Date 반환
     */
    private Date parseDateTime(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        return dateFormat.parse(date);
    }

}
