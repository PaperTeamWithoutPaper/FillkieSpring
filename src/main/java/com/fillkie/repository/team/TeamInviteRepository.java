package com.fillkie.repository.team;

import com.fillkie.domain.team.TeamInvite;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamInviteRepository extends MongoRepository<TeamInvite, String> {
    public Optional<TeamInvite> findByUrl(String id);
}