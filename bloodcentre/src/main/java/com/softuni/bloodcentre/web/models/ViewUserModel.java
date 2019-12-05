package com.softuni.bloodcentre.web.models;

import com.softuni.bloodcentre.data.models.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ViewUserModel {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private List<Department> departments;
    private String position;
    private String phoneNumber;
}
