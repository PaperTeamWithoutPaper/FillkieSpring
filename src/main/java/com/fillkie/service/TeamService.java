package com.fillkie.service;

import com.fillkie.controller.requestDto.CreateTeamDto;
import com.fillkie.domain.Group;
import com.fillkie.domain.GroupUser;
import com.fillkie.domain.teamDomain.Team;
import com.fillkie.repository.GroupRepository;
import com.fillkie.repository.GroupUserRepository;
import com.fillkie.repository.TeamRepository;
import java.util.ArrayList;
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

    @Transactional
    public String saveTeam(CreateTeamDto createTeamDto, String userId){
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


}
