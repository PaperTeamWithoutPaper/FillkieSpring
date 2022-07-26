package com.fillkie.domain.group;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "groupUser")
@Getter
public class GroupUser {

    @Id
    private String id;
    private String teamId;
    private String groupId;
    private String userId;

    @Builder
    public GroupUser(String teamId, String groupId, String userId) {
        this.teamId = teamId;
        this.groupId = groupId;
        this.userId = userId;
    }

}
