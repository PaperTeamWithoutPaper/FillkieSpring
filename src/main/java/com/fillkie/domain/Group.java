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
    private List<String> users = new ArrayList<>();

    @Builder
    public Group(String name, String user) {
        this.name = name;
        users.add(user);
    }

}
