package com.softuni.bloodcentre.data.repositories;

import com.softuni.bloodcentre.data.models.BloodSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BloodSampleRepository extends JpaRepository<BloodSample, Long> {
    List<Optional<BloodSample>> findAllByBloodDonation_Id(long id);
}
