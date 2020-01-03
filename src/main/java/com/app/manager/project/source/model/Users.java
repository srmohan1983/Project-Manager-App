package com.app.manager.project.source.model;


import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;


@Entity
@Table(name="Users_Details")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "userID",scope = Users.class)
public class Users {




    private Long userID;
    private String firstName;
    private String lastName;
    private Long employeeID;
    private Project project;
    private Task task;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "User_ID")
    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Project_ID")
    @JsonIgnore
    //@JsonBackReference
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }


    @Column(name = "First_Name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "Last_Name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "Employee_ID", nullable = false)
    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Task_ID")
    @JsonIgnore
    //@JsonBackReference
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}


