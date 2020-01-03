package com.app.manager.project.source.repository;

import com.app.manager.project.source.model.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {
}
