package com.fillkie.domain.group;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators.In;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "groupPermission")
@Getter
public class GroupPermission {

    @Id
    private String id;
    private String teamId;
    private String groupId;
    private Map<Integer, Boolean> permission;

    @Builder
    public GroupPermission(String teamId, String groupId, Map<Integer, Boolean> permission) {
        this.teamId = teamId;
        this.groupId = groupId;
        this.permission = permission;
    }

    public void acceptPermission(List<Integer> permissionList){
        for(Integer i : permissionList){
            permission.put(i, true);
        }
    }

    public void declinePermission(List<Integer> permissionList){
        for(Integer i : permissionList){
            permission.put(i, false);
        }
    }


}
