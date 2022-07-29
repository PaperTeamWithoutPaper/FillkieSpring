package com.fillkie.security.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@NoArgsConstructor
public class TeamPermission {

    public Integer INVITE_USER;
    public Integer createProject;
    public Integer readTeamSetting;
    public Integer updateUserGroup;
    public Integer updateGroupPermission;
    public Integer updateTeamSetting;
    public Integer deleteTeam;
    public Integer deleteGroup;
    public Integer deleteUser;

}
