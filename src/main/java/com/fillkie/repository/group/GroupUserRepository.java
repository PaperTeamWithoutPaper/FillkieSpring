package com.fillkie.repository.group;

import com.fillkie.domain.group.GroupUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupUserRepository extends MongoRepository<GroupUser, String> {
    public Optional<GroupUser> findById(String id);
    public List<GroupUser> findByTeamIdAndGroupId(String teamId, String groupId);
    public Optional<GroupUser> findByUserIdAndTeamId(String userId, String teamId);
    public Long deleteByUserIdAndGroupId(String userId, String groupId);
    public Long deleteByTeamId(String teamId);
}
