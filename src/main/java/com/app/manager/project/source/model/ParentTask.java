package com.app.manager.project.source.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Parent_Task")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "parentID",scope = ParentTask.class)
public class ParentTask {

    private long parentID;
    private String parentTask;
    private Set<Task> TaskRecords = new HashSet<Task>(
            0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    @JsonIgnore
    //@JsonManagedReference(value="parent-records")
    public Set<Task> getTaskRecords() {
        return TaskRecords;
    }

    public void setTaskRecords(Set<Task> taskRecords) {
        TaskRecords = taskRecords;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Parent_ID")
    public long getParentID() {
        return parentID;
    }

    public void setParentID(long parentID) {
        this.parentID = parentID;
    }

    @Column(name = "Parent_Task", nullable = true)
    public String getParentTask() {
        return parentTask;
    }

    public void setParentTask(String parentTask) {
        this.parentTask = parentTask;
    }
}
