package com.softuni.bloodcentre.service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HospitalServiceModel {
    private long id;
    private String name;
    private String address;
    private String phoneNumber;
}
