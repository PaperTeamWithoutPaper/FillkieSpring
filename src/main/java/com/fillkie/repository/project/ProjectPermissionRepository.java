package com.fillkie.repository.project;

import com.fillkie.domain.project.ProjectPermission;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectPermissionRepository extends MongoRepository<ProjectPermission, String> {

    public Long deleteByTeamId(String teamId);

}
