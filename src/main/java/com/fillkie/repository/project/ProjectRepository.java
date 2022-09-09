package com.fillkie.repository.project;

import com.fillkie.domain.project.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {

    public Long deleteByTeamId(String teamId);
}
