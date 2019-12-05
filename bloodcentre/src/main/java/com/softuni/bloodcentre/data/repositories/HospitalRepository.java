package com.softuni.bloodcentre.data.repositories;

import com.softuni.bloodcentre.data.models.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Optional<Hospital> findHospitalByName(String name);
}
