package com.fillkie.domain.project;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "project")
@Getter
public class ProjectPermission {

    @Id
    private String id;
    private String groupId;
    private String projectId;
    private String teamId;
    // permission?
}
