package com.app.manager.project.source.repository;

import com.app.manager.project.source.model.ParentTask;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ParentTaskRepository  extends CrudRepository<ParentTask, Long> {

    @Query("SELECT p FROM ParentTask p where p.parentID = :ID")
    public ParentTask getParentById(@Param("ID") Long parentId);
}
