package com.app.manager.project.source.repository;

import com.app.manager.project.source.model.Project;
import com.app.manager.project.source.model.Task;
import com.app.manager.project.source.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {

    @Query("SELECT p FROM Task p where p.project = :projectID")
    public List<Task> getTasksByProjectID(@Param("projectID") Project project);

    @Query("SELECT count(p.taskID) FROM Task p where p.project = :projectID")
    public Long getTotalTasksForProjectID(@Param("projectID") Project project);

    @Query("SELECT count(p.taskID) FROM Task p where p.status = :taskStatus and p.project = :projectID")
    public Long getTotalCompletedTasksForProjectID(@Param("taskStatus") String taskStatus, @Param("projectID") Project project);
}
