package com.softuni.bloodcentre.data.repositories;

import com.softuni.bloodcentre.data.models.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {
    Optional<Donor> findByFullName(String fullName);
}
