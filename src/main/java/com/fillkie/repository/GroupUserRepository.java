package com.fillkie.repository;

import com.fillkie.domain.group.GroupUser;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupUserRepository extends MongoRepository<GroupUser, String> {
    public Optional<GroupUser> findById(String id);
}
