package com.fillkie.domain.teamDomain;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "teamRole")
@Getter
public class TeamRole {

    @Id
    private String id;
    private List<String> admin = new ArrayList<>();
    private List<String> member = new ArrayList<>();

    public void addAdmin(String id){
        admin.add(id);
    }

    public void addMember(String id){
        member.add(id);
    }

}
