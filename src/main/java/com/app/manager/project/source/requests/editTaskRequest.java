package com.app.manager.project.source.requests;

import com.app.manager.project.source.model.Task;

public class editTaskRequest {
    private Task task;
    private Long oldUserId;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Long getOldUserId() {
        return oldUserId;
    }

    public void setOldUserId(Long oldUserId) {
        this.oldUserId = oldUserId;
    }
}
