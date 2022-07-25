package com.fillkie.repository;

import com.fillkie.domain.group.Group;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group, String> {
    public Optional<Group> findById(String id);
    public Optional<Group> findByNameAndTeamId(String name, String teamId);
    public List<Group> findByTeamId(String teamId);
}