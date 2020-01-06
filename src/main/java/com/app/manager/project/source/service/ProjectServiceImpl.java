package com.app.manager.project.source.service;

import com.app.manager.project.source.exception.ResourceNotFoundException;
import com.app.manager.project.source.model.Project;
import com.app.manager.project.source.repository.ParentTaskRepository;
import com.app.manager.project.source.repository.ProjectRepository;
import com.app.manager.project.source.repository.TaskRepository;
import com.app.manager.project.source.repository.UsersRepository;
import com.app.manager.project.source.responses.getProjectsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl {
    @Autowired
    private ParentTaskRepository parentTaskRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UsersRepository userRepository;

    public Long getCountofTasks(Project project) {
        Long taskCount = taskRepository.getTotalTasksForProjectID(project);
        return taskCount;
    }

    public Long getCompletedTasksCount(String taskStatus, Project project) {
        Long completedTaskCount = taskRepository.getTotalCompletedTasksForProjectID(taskStatus, project);
        return completedTaskCount;
    }

    public List<getProjectsResponse> getAllProjects() {
        List<Project> projectList = (List<Project>) projectRepository.findAll();
        ArrayList<getProjectsResponse> projectResponseList = new ArrayList<>();

        for (Project p: projectList) {
            getProjectsResponse projectsResponse = new getProjectsResponse();
            projectsResponse.setProject(p);
            projectsResponse.setTasksCount(getCountofTasks(p));
            projectsResponse.setCompletedTasksCount(getCompletedTasksCount("Completed", p));
            projectResponseList.add(projectsResponse);

        }


        return projectResponseList;



    }


}
