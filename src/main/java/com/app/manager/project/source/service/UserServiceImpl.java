package com.app.manager.project.source.service;

import com.app.manager.project.source.model.Users;
import com.app.manager.project.source.repository.UsersRepository;
import com.app.manager.project.source.responses.userDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl {

    @Autowired
    private UsersRepository usersRepository;
    public List<userDetailResponse> getAllUsers() {
        List<Users> userList = new ArrayList<>();
        ArrayList<userDetailResponse> userDetailResponseList = new ArrayList<>();
        userList = (List<Users>) usersRepository.findAll();
        for (Users user: userList) {
            userDetailResponse userDetailResponse = new userDetailResponse();
            userDetailResponse.setUser(user);
/*            if ( user.getProject() != null ) {
            userDetailResponse.setProjectid(user.getProject().getProjectID());
            }*/
            userDetailResponseList.add(userDetailResponse);
        }
        return userDetailResponseList;

    }
}
