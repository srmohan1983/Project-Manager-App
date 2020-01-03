package com.app.manager.project.source.responses;

import com.app.manager.project.source.model.Project;

public class getProjectsResponse {

    private Project project;
    private Long tasksCount;
    private Long completedTasksCount;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Long getTasksCount() {
        return tasksCount;
    }

    public void setTasksCount(Long tasksCount) {
        this.tasksCount = tasksCount;
    }

    public Long getCompletedTasksCount() {
        return completedTasksCount;
    }

    public void setCompletedTasksCount(Long completedTasksCount) {
        this.completedTasksCount = completedTasksCount;
    }
}
