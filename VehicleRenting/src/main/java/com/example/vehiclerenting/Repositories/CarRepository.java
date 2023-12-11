package com.example.vehiclerenting.Repositories;

import com.example.vehiclerenting.Models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    // You can add custom query methods if needed
}
