package com.softuni.bloodcentre.data.repositories;

import com.softuni.bloodcentre.data.models.BloodProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodProductRepository extends JpaRepository<BloodProduct, Long> {
}
