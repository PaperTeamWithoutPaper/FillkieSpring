package com.fillkie.domain.group;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "groupPermission")
@Getter
public class GroupPermission {

    @Id
    private String id;
    private String teamId;
    private String groupId;
    private List<String> permission;

    @Builder
    public GroupPermission(String teamId, String groupId, List<String> permission) {
        this.teamId = teamId;
        this.groupId = groupId;
        this.permission = permission;
    }

    public void addPermission(String permissionName){
        permission.add(permissionName);
    }

}
