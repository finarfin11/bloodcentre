package com.softuni.bloodcentre.services;

import com.softuni.bloodcentre.base.TestBase;
import com.softuni.bloodcentre.data.models.Department;
import com.softuni.bloodcentre.data.models.User;
import com.softuni.bloodcentre.data.repositories.DepartmentRepository;
import com.softuni.bloodcentre.data.repositories.UserRepository;
import com.softuni.bloodcentre.service.models.UserServiceModel;
import com.softuni.bloodcentre.service.services.DepartmentService;
import com.softuni.bloodcentre.service.services.UserService;
import com.softuni.bloodcentre.web.models.EditUserModel;
import com.softuni.bloodcentre.web.models.RegisterUserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.NoResultException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTests extends TestBase {
    private User testUser;
    private Department department;
    private RegisterUserModel registerModel;

    @MockBean
    UserRepository userRepository;

    @MockBean
    DepartmentRepository departmentRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    DepartmentService departmentService;

    List<User> testUsers;
    RegisterUserModel model;
    EditUserModel editModel;

    @BeforeEach
    public void init() {
        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("name");
        testUser.setPosition("position");
        testUser.setPhoneNumber("number");
        testUser.setPassword("password");
        testUser.setFirstName("firstName");
        testUser.setLastName("lastName");
        model = new RegisterUserModel();
        editModel = new EditUserModel();
        Department testDepartment = new Department();
        testDepartment.setName("Administration");
        testUser.setDepartments(new ArrayList<>());
        testUser.getAuthorities().add(testDepartment);
        testUsers = new ArrayList<>();
        department = new Department();
        department.setName("departmentName");
        Mockito.when(userRepository.findById(testUser.getId())).thenReturn(java.util.Optional.of(testUser));
        Mockito.when(userRepository.save(testUser)).thenReturn(testUser);
        Mockito.when(userRepository.findByUsername("name")).thenReturn(java.util.Optional.ofNullable(testUser));
        Mockito.when(userRepository.findAll()).thenReturn(testUsers);
        Mockito.when(departmentRepository.findByName("departmentName")).thenReturn(java.util.Optional.ofNullable(department));
    }

    @Test
    public void user_Service_Find_By_Valid_Id_Should_Return_Correct_Result() {
        User actualUser = userService.findById(1);
        assertEquals(actualUser.getId(), testUser.getId());
    }

    @Test
    public void user_Service_Find_By_Invalid_Id_Should_Throw() {
        assertThrows(NoResultException.class, () -> userService.findById(3));
    }

    @Test
    public void user_Service_Add_User_Should_Return_Correct_Result() {
        registerModel = new RegisterUserModel();
        registerModel.setUsername("uname");
        registerModel.setDepartmentName("departmentName");
        registerModel.setPassword("pass");
        registerModel.setConfirmPassword("pass");
        userService.addUser(registerModel);
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(argumentCaptor.capture());
        User user = argumentCaptor.getValue();
        assertNotNull(user);
        assertEquals(registerModel.getUsername(), user.getUsername());
    }

    @Test
    public void user_Service_Add_User_Should_Work_Correctly() {
        User user =  new User();
        user.setDepartments(new ArrayList<>());
        user.getAuthorities().add(department);
        testUsers.add(user);
        List<UserServiceModel> actualUsers = userService.findAllUsers();
        assertEquals(testUsers.size(), actualUsers.size());
    }

    @Test
    public void user_Service_Find_By_Username_Should_Return_Correct_Result() {
        User actualUser = userService.findByUsername("name");
        assertEquals(testUser, actualUser);
    }

    @Test
    public void user_Service_Find_By_Username_Throw_When_Not_Exists() {
        assertThrows(NoResultException.class, () -> userService.findByUsername("missingName"));
    }

    @Test
    public void user_Service_Edit_User_Should_Work_Correctly() {
        editModel.setUsername("newName");
        editModel.setPosition("newPosition");
        editModel.setPhoneNumber("newNumber");
        editModel.setPassword("newPassword");
        editModel.setConfirmPassword("newPassword");
        editModel.setFirstName("newFirstName");
        editModel.setLastName("newLastName");
        editModel.setDepartmentName("departmentName");
        User editedUser = userService.editUser(editModel, 1);
        assertEquals(editedUser.getFirstName(), testUser.getFirstName());
        assertEquals(editedUser.getLastName(), testUser.getLastName());
        assertEquals(editedUser.getPosition(), testUser.getPosition());
        assertEquals(editedUser.getPhoneNumber(), testUser.getPhoneNumber());
        assertEquals(editedUser.getPassword(), testUser.getPassword());
        assertEquals(editedUser.getUsername(), testUser.getUsername());
        assertEquals(editedUser.getAuthorities(), testUser.getAuthorities());
    }

}
