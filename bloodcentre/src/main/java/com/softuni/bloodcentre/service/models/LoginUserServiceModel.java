package com.softuni.bloodcentre.service.models;

import com.softuni.bloodcentre.data.models.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LoginUserServiceModel {
    private long id;
    private String username;
    private List<Department> departments;
}
