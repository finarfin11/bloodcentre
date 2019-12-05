package com.softuni.bloodcentre.data.models;

import com.softuni.bloodcentre.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "donations")
public class BloodDonation extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "donor_id", nullable = false)
    private Donor donor;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private Date dateOfDonation;
    @OneToMany(mappedBy = "bloodDonation", cascade = CascadeType.ALL)
    private List<BloodSample> bloodSamples;
    @Column
    @Enumerated(EnumType.STRING)
    private BloodProcessState bloodProcessState;
}
