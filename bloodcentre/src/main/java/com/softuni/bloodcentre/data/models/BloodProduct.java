package com.softuni.bloodcentre.data.models;

import com.softuni.bloodcentre.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "blood_products")
public class BloodProduct extends BaseEntity {
    @Column(nullable = false)
    private Date dateOfDonation;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BloodProductType type;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column
    @Enumerated(EnumType.STRING)
    private ProductAvailability productAvailability;
}
