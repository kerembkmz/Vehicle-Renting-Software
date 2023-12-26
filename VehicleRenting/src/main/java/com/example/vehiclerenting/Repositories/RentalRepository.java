package com.example.vehiclerenting.Repositories;

import com.example.vehiclerenting.Models.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByUser_Id(Long userId);

}