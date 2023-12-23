package com.example.vehiclerenting.Repositories;

import com.example.vehiclerenting.Models.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {

}