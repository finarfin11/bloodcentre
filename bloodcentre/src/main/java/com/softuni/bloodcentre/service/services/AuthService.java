package com.softuni.bloodcentre.service.services;

import com.softuni.bloodcentre.service.models.LoginUserServiceModel;
import com.softuni.bloodcentre.web.models.LoginUserModel;

public interface AuthService {
    LoginUserServiceModel login(LoginUserModel model);
}
