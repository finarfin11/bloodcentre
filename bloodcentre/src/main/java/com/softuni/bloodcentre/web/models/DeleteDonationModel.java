package com.softuni.bloodcentre.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class DeleteDonationModel {
    private long id;
    private String donorName;
    private BigDecimal amount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfDonation;
}
