package com.softuni.bloodcentre.data.repositories;

import com.softuni.bloodcentre.data.models.BloodDonation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodDonationRepository extends JpaRepository<BloodDonation, Long> {

//    @Query("select bd from BloodDonation bd where bd.getBloodProcessState = 'UNPROCESSED'")
//    List<BloodDonation> findAllUntestedDonations();

    @Query("select distinct bd from BloodDonation bd join bd.bloodSamples s where s.bloodSampleStatus = 'CLEAN'")
    List<BloodDonation> findAllCleanDonations();
}
