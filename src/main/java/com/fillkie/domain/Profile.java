package com.fillkie.domain;

import com.fillkie.domain.user.Google;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "profile")
@Getter
public class Profile {

    @Id
    private String id;
    private String teamId;
    private String userId;
    private String name;
    private String image;

    @Builder
    public Profile(String teamId, String userId, String name, String image) {
        this.teamId = teamId;
        this.userId = userId;
        this.name = name;
        this.image = image;
    }
}
