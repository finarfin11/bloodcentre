package com.softuni.bloodcentre.web.models;

import com.softuni.bloodcentre.web.validation.ValidationConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class EditRequestPostModel {
    private long id;
    @NotNull(message = ValidationConstants.FIELD_CANNOT_BE_EMPTY)
    @Size(min = 1, max = 40, message = ValidationConstants.COMMON_FIELD_LENGTH)
    private String hospital;
    @NotNull(message = ValidationConstants.FIELD_CANNOT_BE_EMPTY)
    @Size(min = 1, max = 40, message = ValidationConstants.COMMON_FIELD_LENGTH)
    private String patientFullName;
    @NotNull(message = ValidationConstants.FIELD_CANNOT_BE_EMPTY)
    @Size(min = 1, max = 40, message = ValidationConstants.COMMON_FIELD_LENGTH)
    private String patientId;
    @NotNull(message = ValidationConstants.FIELD_CANNOT_BE_EMPTY)
    private String bloodProductType;
    @NotNull(message = ValidationConstants.FIELD_CANNOT_BE_EMPTY)
    @DecimalMin(value = "300.0", message = ValidationConstants.AMOUNT_MUST_BE_IN_RANGE)
    @DecimalMax(value = "1000.0", message = ValidationConstants.AMOUNT_MUST_BE_IN_RANGE)
    private BigDecimal amount;
    @NotNull(message = ValidationConstants.FIELD_CANNOT_BE_EMPTY)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfRequest;
    @NotNull(message = ValidationConstants.FIELD_CANNOT_BE_EMPTY)
    private String requestState;
}
