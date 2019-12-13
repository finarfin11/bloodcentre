package com.softuni.bloodcentre.service.services;

import com.softuni.bloodcentre.service.models.LoginUserServiceModel;
import com.softuni.bloodcentre.web.models.LoginUserModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    LoginUserServiceModel login(LoginUserModel model);
}
