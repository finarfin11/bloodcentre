package com.softuni.bloodcentre.web.models;

import com.softuni.bloodcentre.validation.ValidationConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class EditHospitalModel {
    private String id;
    @NotNull(message = ValidationConstants.FIELD_CANNOT_BE_EMPTY)
    @Size(min = 1, max = 40, message = ValidationConstants.FIELD_LENGTH)
    private String name;
    @NotNull(message = ValidationConstants.FIELD_CANNOT_BE_EMPTY)
    @Size(min = 1, max = 40, message = ValidationConstants.FIELD_LENGTH)
    private String address;
    @NotNull(message = ValidationConstants.FIELD_CANNOT_BE_EMPTY)
    @Size(min = 1, max = 40, message = ValidationConstants.FIELD_LENGTH)
    private String phoneNumber;
}
