package com.fillkie.domain.group;

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
//    private String ownerId;
    private String teamId;


    @Builder
    public Group(String name, String teamId) {
        this.name = name;
//        this.ownerId = ownerId;
        this.teamId = teamId;
    }


}
