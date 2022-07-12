package com.fillkie.repository;

import com.fillkie.domain.Group;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group, String> {
    public Optional<Group> findById(String id);
    public Optional<Group> findByNameAndTeamId(String name, String teamId);
}