package com.softuni.bloodcentre.web.models;

import com.softuni.bloodcentre.data.models.BloodSampleStatus;
import com.softuni.bloodcentre.data.models.BloodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ViewBloodSampleModel {
    private long id;
    private BloodType bloodType;
    private BloodSampleStatus bloodSampleStatus;
    private String notes;
}
