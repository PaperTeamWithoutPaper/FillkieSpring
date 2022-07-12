package com.fillkie.service;

import com.fillkie.controller.dto.CreateTeamDto;
import com.fillkie.domain.teamDomain.Team;
import com.fillkie.domain.teamDomain.TeamRole;
import com.fillkie.repository.team.TeamRepository;
import com.fillkie.repository.team.TeamRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TeamService {

    private final TeamRoleRepository teamRoleRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public String saveTeam(CreateTeamDto createTeamDto, String userId){
        String teamName = createTeamDto.getTeamName();
        TeamRole teamRole = new TeamRole();
        teamRole.addAdmin(userId);

        TeamRole newTeamRole = teamRoleRepository.insert(teamRole);

        Team team = Team.builder()
            .name(teamName)
            .userId(userId)
            .roleId(newTeamRole.getId())
            .build();

        Team newTeam = teamRepository.insert(team);

        return newTeam.getId();

    }


}
