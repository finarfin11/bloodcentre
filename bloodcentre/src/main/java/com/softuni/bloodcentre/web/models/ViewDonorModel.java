package com.softuni.bloodcentre.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ViewDonorModel {
    private long id;
    private String fullName;
    private int age;
    private String sex;
    private String ucn;
    private String idCardNumber;
    private String currentAddress;
    private String phoneNumber;
    private String email;
    private String gpName;
    private String notes;
}
