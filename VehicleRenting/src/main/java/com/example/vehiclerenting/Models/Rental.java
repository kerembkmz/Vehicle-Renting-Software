package com.example.vehiclerenting.Models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "rentals")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long carId; // Car tablosuna referans
    private Long motorcycleId; // Motorcycle tablosuna referans
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;

    // No-argument constructor
    public Rental() {}

    // All-argument constructor
    public Rental(Long id, Long carId, Long motorcycleId, Long userId, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.carId = carId;
        this.motorcycleId = motorcycleId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getCarId() {
        return carId;
    }

    public Long getMotorcycleId() {
        return motorcycleId;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public void setMotorcycleId(Long motorcycleId) {
        this.motorcycleId = motorcycleId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}