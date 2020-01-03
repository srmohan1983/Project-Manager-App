package com.app.manager.project.source.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name="Task_Details")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "taskID",scope = Task.class)
public class Task {

    private long taskID;
    private String task;
    private Date startDate;
    private Date endDate;
    private long priority;
    private String status;
    private Project project;
    private ParentTask parent;
    private Users user;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "task")
    //@JsonManagedReference
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Parent_ID", nullable = false)
    //@JsonBackReference(value="parent-records")
    public ParentTask getParent() {
        return parent;
    }

    public void setParent(ParentTask parent) {
        this.parent = parent;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Task_ID")
    public long getTaskID() {
        return taskID;
    }

    public void setTaskID(long taskID) {
        this.taskID = taskID;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Project_ID", nullable = false)
    //@JsonBackReference(value="project-records")
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Column(name = "Task", nullable = true)
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Column(name = "Start_Date", nullable = true)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name = "End_Date", nullable = true)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(name = "Priority", nullable = true)
    public long getPriority() {
        return priority;
    }

    public void setPriority(long priority) {
        this.priority = priority;
    }

    @Column(name = "Status", nullable = true)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
