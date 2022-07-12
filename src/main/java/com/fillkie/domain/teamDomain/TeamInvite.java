package com.fillkie.domain.teamDomain;

import java.util.List;
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
    private String startDateTime;

    @Builder
    public TeamInvite(String teamId, String url, String startDateTime) {
        this.teamId = teamId;
        this.url = url;
        this.startDateTime = startDateTime;
    }

}
