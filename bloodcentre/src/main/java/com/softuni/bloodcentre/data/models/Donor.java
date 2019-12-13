package com.softuni.bloodcentre.data.models;

import com.softuni.bloodcentre.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "donors")
public class Donor extends BaseEntity {
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private int age;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Column(nullable = false, unique = true)
    private String ucn;
    @Column(nullable = false, unique = true)
    private String idCardNumber;
    @Column(nullable = false)
    private String currentAddress;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String gpName;
    @Column
    private String notes;
    @OneToMany(mappedBy = "donor")
    private List<BloodDonation> donations;

}
