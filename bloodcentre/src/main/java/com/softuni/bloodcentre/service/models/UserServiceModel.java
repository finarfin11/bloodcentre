package com.softuni.bloodcentre.service.models;

import com.softuni.bloodcentre.data.models.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserServiceModel {
    private long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private List<Department> departments;
    private String position;
    private String phoneNumber;
}
