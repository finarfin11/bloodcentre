package com.softuni.bloodcentre.data.models;

import com.softuni.bloodcentre.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "blood_samples")
public class BloodSample extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "blood_donation_id")
    private BloodDonation bloodDonation;
    @Column
    @Enumerated(EnumType.STRING)
    private BloodType bloodType;
    @Column
    @Enumerated(EnumType.STRING)
    private BloodSampleStatus bloodSampleStatus;
    @Column
    private String notes;
}
