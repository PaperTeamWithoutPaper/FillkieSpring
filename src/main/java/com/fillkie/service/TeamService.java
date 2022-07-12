package com.fillkie.service;

import com.fillkie.controller.requestDto.CreateTeamReqDto;
import com.fillkie.domain.Group;
import com.fillkie.domain.GroupUser;
import com.fillkie.domain.teamDomain.Team;
import com.fillkie.domain.teamDomain.TeamInvite;
import com.fillkie.repository.GroupRepository;
import com.fillkie.repository.GroupUserRepository;
import com.fillkie.repository.TeamInviteRepository;
import com.fillkie.repository.TeamRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

        Group professor = Group.builder()
            .name("professor")
            .teamId(team.getId())
            .users(new ArrayList<>())
            .roles(new ArrayList<>())
            .build();
        professor.addUser(userId);
        professor = groupRepository.insert(professor);

        Group doctor = Group.builder()
            .name("doctor")
            .teamId(team.getId())
            .users(new ArrayList<>())
            .roles(new ArrayList<>())
            .build();
        doctor = groupRepository.insert(doctor);

        Group master = Group.builder()
            .name("master")
            .teamId(team.getId())
            .users(new ArrayList<>())
            .roles(new ArrayList<>())
            .build();
        master = groupRepository.insert(master);

        Group intern = Group.builder()
            .name("intern")
            .teamId(team.getId())
            .users(new ArrayList<>())
            .roles(new ArrayList<>())
            .build();
        intern = groupRepository.insert(intern);

        GroupUser groupUser = GroupUser.builder()
            .teamId(team.getId())
            .groupId(professor.getId())
            .userId(userId)
            .build();
        groupUserRepository.insert(groupUser);

        return team.getId();

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
            return null;
        }

        // Team에 user 추가
        Team team = teamRepository.findById(teamInvite.getTeamId()).orElseThrow(RuntimeException::new);
        team.addUser(userId);
        teamRepository.insert(team);

        // Group "intern"에 user 추가
        Group group = groupRepository.findByNameAndTeamId("intern", team.getId()).orElseThrow(RuntimeException::new);
        group.addUser(userId);
        groupRepository.insert(group);

        // GroupUser 생성
        GroupUser groupUser = GroupUser.builder()
            .teamId(team.getId())
            .groupId(group.getId())
            .userId(userId)
            .build();
        groupUserRepository.insert(groupUser);

        return team.getId();
    }

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
