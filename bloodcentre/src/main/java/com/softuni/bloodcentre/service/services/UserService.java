package com.softuni.bloodcentre.service.services;

import com.softuni.bloodcentre.data.models.User;
import com.softuni.bloodcentre.service.models.UserServiceModel;
import com.softuni.bloodcentre.web.models.EditUserModel;
import com.softuni.bloodcentre.web.models.RegisterUserModel;

import java.util.List;

public interface UserService {
    List<UserServiceModel> findAllUsers();
    User addUser(RegisterUserModel registerUserModel);
    User findById(long id);
    User editUser(EditUserModel model, long id);
    void deleteUser(long id);
    User findByUsername(String username);
}
