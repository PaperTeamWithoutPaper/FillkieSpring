package com.fillkie.domain.team;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "teamInvite")
@Getter
public class TeamInvite {

    @Id
    private String id;
    private String teamId;
    private String url;
    private Long expiryDate;

    @Builder
    public TeamInvite(String teamId, String url, Long expiryDate) {
        this.teamId = teamId;
        this.url = url;
        this.expiryDate = expiryDate;
    }

}
