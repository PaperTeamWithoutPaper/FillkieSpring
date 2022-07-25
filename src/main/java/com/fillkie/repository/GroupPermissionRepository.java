package com.fillkie.repository;

import com.fillkie.domain.group.Group;
import com.fillkie.domain.group.GroupPermission;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupPermissionRepository extends MongoRepository<GroupPermission, String> {
    public Optional<GroupPermission> findByGroupId(String groupId);
}
