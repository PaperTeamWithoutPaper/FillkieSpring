package com.fillkie.repository.team;

import com.fillkie.domain.team.Team;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepository extends MongoRepository<Team, String> {
    public Optional<Team> findById(String id);
    public void deleteById(String id);
}
