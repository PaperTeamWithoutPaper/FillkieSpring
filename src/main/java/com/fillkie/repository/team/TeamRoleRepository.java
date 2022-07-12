package com.fillkie.repository.team;

import com.fillkie.domain.teamDomain.TeamRole;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRoleRepository  extends MongoRepository<TeamRole, String> {
    public Optional<TeamRole> findById(String id);

}
