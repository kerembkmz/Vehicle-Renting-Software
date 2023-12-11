package com.example.vehiclerenting.Repositories;

import com.example.vehiclerenting.Models.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {
    // You can add custom query methods if needed
}
