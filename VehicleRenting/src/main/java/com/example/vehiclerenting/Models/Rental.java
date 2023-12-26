package com.example.vehiclerenting.Models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Optional;

@Entity
@Table(name = "rental", schema = "VRS")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "motorcycle_id")
    private Motorcycle motorcycle;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate startDate;
    private LocalDate endDate;


    // Constructors
    public Rental() {
        // Default constructor
    }

    public Rental(Car car, Motorcycle motorcycle, User user, LocalDate startDate, LocalDate endDate) {
        this.car = car;
        this.motorcycle = motorcycle;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Rental(User user) {
        this.user = user;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getCarId() {
        return this.car.getId();
    }

    public Car getCar() { return this.car;}

    public Motorcycle getMotorcycle(){ return this.motorcycle;}

    public User getUser() {return this.user;}

    public Long getMotorcycleId() {
        return this.motorcycle.getId();
    }

    public Long getUserId() {
        return this.user.getId();
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
        this.car.setId(carId);
    }

    public void setCar(Car car) {this.car = car;}
    public void setMotorcycle(Motorcycle motorcycle) {this.motorcycle = motorcycle;}

    public void setMotorcycleId(Long motorcycleId) {
        this.motorcycle.setId(motorcycleId);
    }

    public void setUserId(Long userId) {
        this.user.setId(userId);
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}