package com.softuni.bloodcentre.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeleteUserModel {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String position;
    private String phoneNumber;
}
