package com.softuni.bloodcentre.service.impl;

import com.softuni.bloodcentre.data.models.Department;
import com.softuni.bloodcentre.data.models.User;
import com.softuni.bloodcentre.data.repositories.UserRepository;
import com.softuni.bloodcentre.service.models.RegisterUserServiceModel;
import com.softuni.bloodcentre.service.models.UserServiceModel;
import com.softuni.bloodcentre.service.services.DepartmentService;
import com.softuni.bloodcentre.service.services.UserService;
import com.softuni.bloodcentre.web.models.EditUserModel;
import com.softuni.bloodcentre.web.models.RegisterUserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final DepartmentService departmentService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, DepartmentService departmentService, BCryptPasswordEncoder bCryptPasswordEncoder, BCryptPasswordEncoder bCryptPasswordEncoder1) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.departmentService = departmentService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder1;
    }

    @Override
    public List<UserServiceModel> findAllUsers() {
        List<User> users = this.userRepository.findAll();
        List<UserServiceModel> serviceModels = users.stream().map(user -> this.modelMapper.map(user, UserServiceModel.class)).collect(Collectors.toList());
        serviceModels.forEach(m -> m.setDepartments(new ArrayList<>()));
        for (int i = 0; i < users.size(); i++) {
            for (int j = 0; j < users.get(i).getAuthorities().size(); j++) {
                serviceModels.get(i).getDepartments().add(users.get(i).getAuthorities().get(j));
            }
        }

        return serviceModels;
    }

    @Override
    @Transactional
    public User addUser(RegisterUserModel registerUserModel) {
        if (!registerUserModel.getPassword().equals(registerUserModel.getConfirmPassword())) {
            throw new IllegalArgumentException("Confirm password does not match!");
        }

        RegisterUserServiceModel registerUserServiceModel = this.modelMapper.map(registerUserModel, RegisterUserServiceModel.class);
        if (this.userRepository.findByUsername(registerUserServiceModel.getUsername()).isPresent()) {
            throw new DuplicateKeyException("User with the given username already exists!");
        }

        registerUserServiceModel.setDepartments(new ArrayList<>());
        Department department = this.departmentService.findByName(registerUserModel.getDepartmentName());
        registerUserServiceModel.getDepartments().add(department);
        registerUserServiceModel.setPassword(bCryptPasswordEncoder.encode(registerUserModel.getPassword()));
        return this.userRepository.save(this.modelMapper.map(registerUserServiceModel, User.class));
    }

    public User findById(long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new NoResultException("User with the given id was not found!"));
    }

    @Override
    public User editUser(EditUserModel editUserModel, long id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new NoResultException("User with the given id was not found!"));
        Department department = this.departmentService.findByName(editUserModel.getDepartmentName());
        if (editUserModel.getPassword().isBlank() || !editUserModel.getPassword().equals(editUserModel.getConfirmPassword())) {
            throw new IllegalArgumentException("Invalid password!");
        }

        user.setFirstName(editUserModel.getFirstName());
        user.setLastName(editUserModel.getLastName());
        user.setPassword(bCryptPasswordEncoder.encode(editUserModel.getPassword()));
        List<Department> currentDepartments = user.getAuthorities();
        if (!currentDepartments.contains(department)) {
            currentDepartments.add(department);
        }
        user.setDepartments(user.getAuthorities().stream().filter(d -> !currentDepartments.contains(d)).collect(Collectors.toList()));
        user.getAuthorities().addAll(currentDepartments);
        user.setPhoneNumber(editUserModel.getPhoneNumber());
        user.setUsername(editUserModel.getUsername());
        user.setPosition(editUserModel.getPosition());
        return this.userRepository.save(user);
    }

    @Override
    public void deleteUser(long id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new NoResultException("User with the given id was not found!"));
        this.userRepository.delete(user);
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new NoResultException("User with the given username was not found!"));
    }
}
