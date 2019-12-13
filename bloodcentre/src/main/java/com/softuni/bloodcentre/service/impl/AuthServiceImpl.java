package com.softuni.bloodcentre.service.impl;

import com.softuni.bloodcentre.data.models.User;
import com.softuni.bloodcentre.data.repositories.UserRepository;
import com.softuni.bloodcentre.service.models.LoginUserServiceModel;
import com.softuni.bloodcentre.service.services.AuthService;
import com.softuni.bloodcentre.web.models.LoginUserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

@Service
public class AuthServiceImpl implements AuthService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthServiceImpl(ModelMapper modelMapper, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public LoginUserServiceModel login(LoginUserModel model) {
        User user = this.userRepository.findByUsernameAndPassword(model.getUsername(), bCryptPasswordEncoder.encode(model.getPassword())).orElseThrow(() -> new NoResultException("Invalid credentials!"));
        return this.modelMapper.map(user, LoginUserServiceModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Invalid credentials!"));
    }
}
