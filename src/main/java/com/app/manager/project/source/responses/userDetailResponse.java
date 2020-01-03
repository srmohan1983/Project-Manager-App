package com.app.manager.project.source.responses;

import com.app.manager.project.source.model.Users;

public class userDetailResponse {

    private Users user;
    //private long projectid;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    /*public long getProjectid() {
        return projectid;
    }

    public void setProjectid(long projectid) {
        this.projectid = projectid;
    }*/
}
