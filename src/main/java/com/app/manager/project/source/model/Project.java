package com.app.manager.project.source.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Project_Details")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "projectID",scope = Project.class)
public class Project {

    private Long projectID;
    private String projectTitle;
    private Date startDate;
    private Date endDate;
    private Long priority;
    private Set<Task> TaskRecords = new HashSet<Task>(
            0);
    private Set<Users> userRecords = new HashSet<Users>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
    public Set<Users> getUserRecords() {
        return userRecords;
    }

    public void setUserRecords(Set<Users> userRecords) {
        this.userRecords = userRecords;
    }



    @Column(name = "Project_Title", nullable = false)
    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Project_ID")
    public Long getProjectID() {
        return projectID;
    }

    public void setProjectID(Long projectID) {
        this.projectID = projectID;
    }

    @Column(name = "Start_Date", nullable = false)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name = "End_Date", nullable = false)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(name = "Priority", nullable = false)
    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    @JsonIgnore
    //@JsonManagedReference(value="project-records")
    public Set<Task> getTaskRecords() {
        return TaskRecords;
    }

    public void setTaskRecords(Set<Task> taskRecords) {
        TaskRecords = taskRecords;
    }
}
