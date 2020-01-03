package com.app.manager.project.source.repository;

import com.app.manager.project.source.model.Project;
import com.app.manager.project.source.model.Task;
import com.app.manager.project.source.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersRepository extends CrudRepository<Users, Long> {

    @Query("SELECT p FROM Users p where p.task = :task")
    public Users getUsersByTaskID(@Param("task") Task task);
}
