package com.softuni.bloodcentre.service.models;

import com.softuni.bloodcentre.data.models.BloodProcessState;
import com.softuni.bloodcentre.data.models.BloodSample;
import com.softuni.bloodcentre.data.models.Donor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BloodDonationServiceModel {
    private long id;
    private Donor donor;
    private BigDecimal amount;
    private Date dateOfDonation;
    private List<BloodSample> bloodSamples;
    private BloodProcessState bloodProcessState;
}
