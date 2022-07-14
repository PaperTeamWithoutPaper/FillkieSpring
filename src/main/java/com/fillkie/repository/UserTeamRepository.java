package com.fillkie.repository;

import com.fillkie.domain.UserTeam;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserTeamRepository extends MongoRepository<UserTeam, String> {

    public Optional<UserTeam> findById(String email);
    public Optional<UserTeam> findByUserId(String userId);
}
