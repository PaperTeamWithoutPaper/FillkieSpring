package com.fillkie.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "group")
@Getter
public class Group {

    @Id
    private String id;
    private String name;
    private String teamId;
    private List<String> users;
    private List<String> roles;


    @Builder
    public Group(String name, String teamId, List<String> users, List<String> roles) {
        this.name = name;
        this.teamId = teamId;
        this.users = users;
        this.roles = roles;
    }

    public String addUser(String userId){
        users.add(userId);
        return userId;
    }

    public String addRole(String role){
        roles.add(role);
        return role;
    }

}
