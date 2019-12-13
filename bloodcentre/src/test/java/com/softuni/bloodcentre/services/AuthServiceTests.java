package com.softuni.bloodcentre.services;

import com.softuni.bloodcentre.base.TestBase;
import com.softuni.bloodcentre.data.models.User;
import com.softuni.bloodcentre.data.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.softuni.bloodcentre.service.models.LoginUserServiceModel;
import com.softuni.bloodcentre.service.services.AuthService;
import com.softuni.bloodcentre.web.models.LoginUserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AuthServiceTests extends TestBase {
    private User testUser;

    @MockBean
    UserRepository userRepository;

    @MockBean
    ModelMapper modelMapper;

    @Autowired
    AuthService authService;

    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void init() {
        testUser = new User();
        testUser.setUsername("username");
        testUser.setPassword("password");
        Mockito.when(userRepository.findByUsernameAndPassword(testUser.getUsername(), bCryptPasswordEncoder.encode(testUser.getPassword()))).thenReturn(java.util.Optional.ofNullable(testUser));

    }

    @Test
    public void auth_Service_Login_Should_Return_Correct_Value() {
        LoginUserModel loginUserModel = new LoginUserModel();
        loginUserModel.setUsername("username");
        loginUserModel.setPassword("password");
        LoginUserServiceModel serviceModel = authService.login(loginUserModel);
        assertEquals(serviceModel, modelMapper.map(testUser, LoginUserServiceModel.class));
    }

}
