package com.fillkie.repository;

import com.fillkie.domain.teamDomain.Team;
import com.fillkie.domain.teamDomain.TeamInvite;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamInviteRepository extends MongoRepository<TeamInvite, String> {
    public Optional<TeamInvite> findByUrl(String id);
}