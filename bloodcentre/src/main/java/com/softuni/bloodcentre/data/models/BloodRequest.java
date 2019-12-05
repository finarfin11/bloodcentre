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
@Table(name = "blood_requests")
public class BloodRequest extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;
    @Column(nullable = false)
    private String patientFullName;
    @Column(nullable = false)
    private String patientId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BloodProductType bloodProductType;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private Date dateOfRequest;
    @Column
    @Enumerated(EnumType.STRING)
    private RequestState requestState;
}
