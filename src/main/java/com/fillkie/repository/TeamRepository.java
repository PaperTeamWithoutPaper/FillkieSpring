package com.fillkie.repository;

import com.fillkie.domain.teamDomain.Team;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepository extends MongoRepository<Team, String> {
    public Optional<Team> findById(String id);
}
