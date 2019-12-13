package com.softuni.bloodcentre.web.models;

import com.softuni.bloodcentre.data.models.Sex;
import com.softuni.bloodcentre.web.validation.ValidationConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class EditDonorModel {
    private long id;
    @Size(min = 1, max = 40, message = ValidationConstants.COMMON_FIELD_LENGTH)
    private String fullName;
    @NotNull(message = ValidationConstants.FIELD_CANNOT_BE_EMPTY)
    @Min(value = 18, message = ValidationConstants.AGE_FIELD_MIN_MAX)
    @Max(value = 65, message = ValidationConstants.AGE_FIELD_MIN_MAX)
    private int age;
    @NotNull
    private Sex sex;
    @Size(min = 1, max = 40, message = ValidationConstants.COMMON_FIELD_LENGTH)
    private String ucn;
    @Size(min = 1, max = 40, message = ValidationConstants.COMMON_FIELD_LENGTH)
    private String idCardNumber;
    @Size(min = 1, max = 40, message = ValidationConstants.COMMON_FIELD_LENGTH)
    private String currentAddress;
    @Size(min = 1, max = 40, message = ValidationConstants.COMMON_FIELD_LENGTH)
    private String phoneNumber;
    @NotBlank(message = ValidationConstants.FIELD_CANNOT_BE_EMPTY)
    @Email(message = ValidationConstants.EMAIL_ADDRESS_MUST_BE_VALID)
    private String email;
    @Size(min = 1, max = 40, message = ValidationConstants.COMMON_FIELD_LENGTH)
    private String gpName;
    @Size(max = 400, message = ValidationConstants.COMMON_FIELD_LENGTH)
    private String notes;
}
