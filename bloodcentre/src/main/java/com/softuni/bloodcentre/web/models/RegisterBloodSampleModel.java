package com.softuni.bloodcentre.web.models;

import com.softuni.bloodcentre.data.models.BloodSampleStatus;
import com.softuni.bloodcentre.data.models.BloodType;
import com.softuni.bloodcentre.validation.ValidationConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class RegisterBloodSampleModel {
//    private long id;
    @NotNull(message = ValidationConstants.FIELD_CANNOT_BE_EMPTY)
    private BloodType bloodType;
    @NotNull(message = ValidationConstants.FIELD_CANNOT_BE_EMPTY)
    private BloodSampleStatus bloodSampleStatus;
    @NotNull(message = ValidationConstants.FIELD_CANNOT_BE_EMPTY)
    @Size(max = 40, message = ValidationConstants.FIELD_MAX)
    private String notes;
}
