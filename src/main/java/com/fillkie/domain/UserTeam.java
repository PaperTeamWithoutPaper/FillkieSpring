package com.fillkie.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userTeam")
@Getter
public class UserTeam {

    @Id
    private String id;
    private String userId;
    private String teamId;
    private String teamName;

    @Builder
    public UserTeam(String userId, String teamId, String teamName) {
        this.userId = userId;
        this.teamId = teamId;
        this.teamName = teamName;
    }
}
