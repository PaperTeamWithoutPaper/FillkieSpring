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
    public Integer CREAT_PROJECT;
    public Integer CREATE_GROUP;
    public Integer UPDATE_GROUP_USER_PERMISSION;
    public Integer UPDATE_TEAM_SETTING;
    public Integer DELETE_TEAM;
    public Integer DELETE_GROUP;
    public Integer DELETE_USER;

}
