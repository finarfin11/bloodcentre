package com.softuni.bloodcentre.data.repositories;

import com.softuni.bloodcentre.data.models.BloodRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {
}
