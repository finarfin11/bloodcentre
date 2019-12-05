package com.softuni.bloodcentre.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ViewHospitalModel {
    private long id;
    private String name;
    private String address;
    private String phoneNumber;
}
