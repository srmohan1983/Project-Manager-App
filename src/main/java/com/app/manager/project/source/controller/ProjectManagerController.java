package com.app.manager.project.source.controller;

import com.app.manager.project.source.exception.ResourceNotFoundException;
import com.app.manager.project.source.model.ParentTask;
import com.app.manager.project.source.model.Project;
import com.app.manager.project.source.model.Task;
import com.app.manager.project.source.model.Users;
import com.app.manager.project.source.repository.ParentTaskRepository;
import com.app.manager.project.source.repository.ProjectRepository;
import com.app.manager.project.source.repository.TaskRepository;
import com.app.manager.project.source.repository.UsersRepository;
import com.app.manager.project.source.requests.editTaskRequest;
import com.app.manager.project.source.responses.editTaskResponse;
import com.app.manager.project.source.responses.getProjectsResponse;
import com.app.manager.project.source.responses.userDetailResponse;
import com.app.manager.project.source.responses.viewTaskResponse;
import com.app.manager.project.source.service.ProjectServiceImpl;
import com.app.manager.project.source.service.TaskServiceImpl;
import com.app.manager.project.source.service.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.annotations.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.*;

@RestController
@RequestMapping("/api/v1/projectmanager")
public class ProjectManagerController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ParentTaskRepository parentRepository;

    @Autowired
    private TaskRepository tasksRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TaskServiceImpl taskService;

    @Autowired
    private ProjectServiceImpl projectService;




    @GetMapping("/getUsers")
    public List<Users> getAllUsers() {

        //return userService.getAllUsers();
        return (List<Users>) usersRepository.findAll();
    }

    @PostMapping("/createUser")
    public Users createUser(@Valid @RequestBody Users user) {
        return usersRepository.save(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable(value = "id") Long userId,
                                                   @Valid @RequestBody Users userDetails) throws ResourceNotFoundException {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + userId));

        user.setEmployeeID(userDetails.getEmployeeID());
        user.setLastName(userDetails.getLastName());
        user.setFirstName(userDetails.getFirstName());
        final Users updatedUser = usersRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }


    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + userId));

        usersRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @GetMapping("/getProjects")
    public List<getProjectsResponse> getAllProjects() {
        return projectService.getAllProjects();
    }


    @GetMapping("/projects/{id}")
    public List<viewTaskResponse> getTasksByProjectId(@PathVariable(value = "id") Long projectId)
            throws ResourceNotFoundException {

        return taskService.getTasksByProjectId(projectId);
    }

    @GetMapping("/tasks/{id}")
    public editTaskResponse getTasksByTaskId(@PathVariable(value = "id") Long taskId)
            throws ResourceNotFoundException {

        return taskService.getTasksByTaskId(taskId);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable(value = "id") Long oldUserId,
                                            @Valid @RequestBody Task taskDetails) throws ResourceNotFoundException {
        Task task = tasksRepository.findById(taskDetails.getTaskID())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + oldUserId));

        task.setTask(taskDetails.getTask());
        task.setPriority(taskDetails.getPriority());
        task.setStartDate(taskDetails.getStartDate());
        task.setEndDate(taskDetails.getEndDate());
        final Task updatedTask = tasksRepository.save(task);

        long userId = taskDetails.getUser().getUserID();
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + userId));
        user.setTask(updatedTask);
        user.setProject(updatedTask.getProject());
        final Users updatedUser = usersRepository.save(user);

        Users oldUser = usersRepository.findById(oldUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + userId));
        oldUser.setProject(null);
        oldUser.setTask(null);
        final Users updatedOldUser = usersRepository.save(oldUser);

        return ResponseEntity.ok(updatedTask);
    }

    @PutMapping("/tasks/endTasks/{id}")
    public ResponseEntity<Task> endTaskStatus(@PathVariable(value = "id") Long taskID,
                                           @Valid @RequestBody Task taskDetails) throws ResourceNotFoundException {
        Task task = tasksRepository.findById(taskID)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + taskID));

        task.setStatus("Completed");
        final Task updatedTask = tasksRepository.save(task);
        return ResponseEntity.ok(updatedTask);
    }

    @PostMapping("/createProject")
    public Project createProject(@Valid @RequestBody Project project) throws ResourceNotFoundException {

        System.out.print("Project JSON" + project);
        Project newProject = projectRepository.save(project);
        Users passedUSer = project.getUserRecords().iterator().next();
        long userId = passedUSer.getUserID();
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + userId));
        user.setProject(newProject);
        final Users updatedUser = usersRepository.save(user);
        return projectRepository.save(project);
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable(value = "id") Long projectId,
                                            @Valid @RequestBody Project projectDetails) throws ResourceNotFoundException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + projectId));

        project.setProjectTitle(projectDetails.getProjectTitle());
        project.setPriority(projectDetails.getPriority());
        project.setStartDate(projectDetails.getStartDate());
        project.setEndDate(projectDetails.getEndDate());
        project.setUserRecords(projectDetails.getUserRecords());
        final Project updatedProject = projectRepository.save(project);
        return ResponseEntity.ok(updatedProject);
    }


    @GetMapping("/tasks/getTasks")
    public List<Task> getAllTasks() {
        return (List<Task>) tasksRepository.findAll();
    }

    @PostMapping("/tasks/createTask")
    public Task createTask(@Valid @RequestBody Task task) throws ResourceNotFoundException {
        System.out.print("Task JSON" + task);
        Task newTask = tasksRepository.save(task);
        long userId = task.getUser().getUserID();
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + userId));
        user.setTask(newTask);
        user.setProject(newTask.getProject());
        final Users updatedUser = usersRepository.save(user);
        return newTask;
    }

    @GetMapping("/tasks/getParentTasks")
    public List<ParentTask> getAllParentTasks() {
        return (List<ParentTask>) parentRepository.findAll();
    }

    @PostMapping("tasks/createParentTask")
    public ParentTask createParentTask(@Valid @RequestBody ParentTask parentTask) {
        return parentRepository.save(parentTask);
    }



}
