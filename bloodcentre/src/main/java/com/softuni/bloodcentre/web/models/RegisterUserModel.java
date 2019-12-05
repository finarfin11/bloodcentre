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
public class RegisterUserModel {
    @NotNull(message = ValidationConstants.FIELD_CANNOT_BE_EMPTY)
    @Size(min = 1, max = 40, message = ValidationConstants.FIELD_LENGTH)
    private String username;
    @NotNull(message = ValidationConstants.PASSWORD_LENGTH)
    @Size(min = 8, max = 16, message = ValidationConstants.FIELD_LENGTH)
    private String password;
    private String confirmPassword;
    @NotNull(message = ValidationConstants.FIELD_CANNOT_BE_EMPTY)
    @Size(min = 1, max = 40, message = ValidationConstants.FIELD_LENGTH)
    private String firstName;
    @NotNull(message = ValidationConstants.FIELD_CANNOT_BE_EMPTY)
    @Size(min = 1, max = 40, message = ValidationConstants.FIELD_LENGTH)
    private String lastName;
    @NotNull(message = ValidationConstants.FIELD_CANNOT_BE_EMPTY)
    private String departmentName;
    @Size(min = 1, max = 40, message = ValidationConstants.FIELD_LENGTH)
    @NotNull(message = ValidationConstants.FIELD_CANNOT_BE_EMPTY)
    private String position;
    @Size(min = 1, max = 40, message = ValidationConstants.FIELD_LENGTH)
    @NotNull(message = ValidationConstants.FIELD_CANNOT_BE_EMPTY)
    private String phoneNumber;
}
