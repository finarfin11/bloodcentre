package com.softuni.bloodcentre.service.models;

import com.softuni.bloodcentre.data.models.BloodProductType;
import com.softuni.bloodcentre.data.models.Hospital;
import com.softuni.bloodcentre.data.models.RequestState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class BloodRequestServiceModel {
    private long id;
    private Hospital hospital;
    private String patientFullName;
    private String patientId;
    private BloodProductType bloodProductType;
    private BigDecimal amount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfRequest;
    private RequestState requestState;
}
