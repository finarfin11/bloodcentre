package com.softuni.bloodcentre.web.models;

import com.softuni.bloodcentre.data.models.BloodProductType;
import com.softuni.bloodcentre.data.models.ProductAvailability;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class EditProductModel {
    private long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfDonation;
    private BigDecimal amount;
    private ProductAvailability productAvailability;
    private BloodProductType type;
}
