package com.app.manager.project.source.service;

import com.app.manager.project.source.exception.ResourceNotFoundException;
import com.app.manager.project.source.model.ParentTask;
import com.app.manager.project.source.model.Project;
import com.app.manager.project.source.model.Task;
import com.app.manager.project.source.model.Users;
import com.app.manager.project.source.repository.ParentTaskRepository;
import com.app.manager.project.source.repository.ProjectRepository;
import com.app.manager.project.source.repository.TaskRepository;
import com.app.manager.project.source.repository.UsersRepository;
import com.app.manager.project.source.responses.editTaskResponse;
import com.app.manager.project.source.responses.viewTaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl {
    private List<viewTaskResponse> viewTaskResponseList;
    @Autowired
    private ParentTaskRepository parentTaskRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UsersRepository userRepository;

    public ParentTask getParentTaskById(Long parentId) {
        ParentTask parentForTask = parentTaskRepository.getParentById(parentId);
        return parentForTask;
    }

    public List<Task> getTaskByProjectId(Project project) {
        List<Task> taskListForProject = taskRepository.getTasksByProjectID(project);
        return taskListForProject;
    }

    public Task getTaskByTaskID(Long taskId) throws ResourceNotFoundException {
        Task taskForId = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + taskId));
        return taskForId;
    }

    public Users getUserByTaskID(Task task) throws ResourceNotFoundException {
        Users userForId = userRepository.getUsersByTaskID(task);
        return userForId;
    }

    public Project getProjectByProjectID(Long projectId) throws ResourceNotFoundException {
        Project projectForId = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + projectId));
        return projectForId;
    }

    public List<viewTaskResponse> getTasksByProjectId(Long projectId) throws ResourceNotFoundException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + projectId));
        List<Task> taskList = getTaskByProjectId(project);
        ArrayList<viewTaskResponse> viewTaskResponseList = new ArrayList<>();
        for(Task t: taskList) {
            List<viewTaskResponse> viewTaskResponseList1 = new ArrayList<>();
            long parentId = t.getParent().getParentID();
            ParentTask parentTask = getParentTaskById(parentId);
            viewTaskResponse viewTaskResp = new viewTaskResponse();
            viewTaskResp.setTask(t.getTask());
            viewTaskResp.setTaskID(t.getTaskID());
            viewTaskResp.setStartDate(t.getStartDate());
            viewTaskResp.setEndDate(t.getEndDate());
            viewTaskResp.setPriority(t.getPriority());
            viewTaskResp.setStatus(t.getStatus());
            viewTaskResp.setParentID(parentTask.getParentID());
            viewTaskResp.setParentTask(parentTask.getParentTask());
            viewTaskResponseList.add(viewTaskResp);

        }
        return viewTaskResponseList;
    }

    public editTaskResponse getTasksByTaskId(Long taskId) throws ResourceNotFoundException {
        Task task = getTaskByTaskID(taskId);
        long parentId = task.getParent().getParentID();
        ParentTask parentTask = getParentTaskById(parentId);
        long projectId = task.getProject().getProjectID();
        Project project = getProjectByProjectID(projectId);
        Users user = getUserByTaskID(task);

        editTaskResponse editTaskResp = new editTaskResponse();
        editTaskResp.setTask(task.getTask());
        editTaskResp.setTaskID(task.getTaskID());
        editTaskResp.setStartDate(task.getStartDate());
        editTaskResp.setEndDate(task.getEndDate());
        editTaskResp.setPriority(task.getPriority());
        editTaskResp.setStatus(task.getStatus());
        editTaskResp.setParentID(parentTask.getParentID());
        editTaskResp.setParentTask(parentTask.getParentTask());
        editTaskResp.setProjectID(project.getProjectID());
        editTaskResp.setProjectTitle(project.getProjectTitle());
        if (user !=null ) {
            editTaskResp.setUserID(user.getUserID());
            editTaskResp.setFirstName(user.getFirstName());
            editTaskResp.setLastName(user.getLastName());
        }


        return editTaskResp;
    }
}
