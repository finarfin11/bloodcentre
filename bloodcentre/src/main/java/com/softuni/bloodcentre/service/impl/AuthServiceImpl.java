package com.softuni.bloodcentre.service.impl;

import com.softuni.bloodcentre.data.models.User;
import com.softuni.bloodcentre.data.repositories.UserRepository;
import com.softuni.bloodcentre.service.models.LoginUserServiceModel;
import com.softuni.bloodcentre.service.services.AuthService;
import com.softuni.bloodcentre.web.models.LoginUserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public AuthServiceImpl(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public LoginUserServiceModel login(LoginUserModel model) {
        User user = this.userRepository.findByUsernameAndPassword(model.getUsername(), model.getPassword()).orElseThrow(() -> new NoResultException("User with the given username was not found!"));
        return this.modelMapper.map(user, LoginUserServiceModel.class);
    }
}
