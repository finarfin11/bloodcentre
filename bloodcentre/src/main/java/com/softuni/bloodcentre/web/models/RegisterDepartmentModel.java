package com.softuni.bloodcentre.web.models;

import com.softuni.bloodcentre.validation.ValidationConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class RegisterDepartmentModel {
    @Size(min = 1, max = 40, message = ValidationConstants.FIELD_LENGTH)
    private String name;
}
