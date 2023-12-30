package com.example.vehiclerenting.Models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@EntityScan("com.example.vehiclerenting.Models")
@Table(name = "Car", schema = "VRS")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String color;
    private int horsepower;
    private int pricePerDay;
    private boolean available;

    @OneToMany(mappedBy = "car")
    private List<Rental> rentals;


    private LocalDate startAvailabilityDate;


    private LocalDate endAvailabilityDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    public int getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public LocalDate getStartAvailabilityDate() {
        return startAvailabilityDate;
    }

    public void setStartAvailabilityDate(LocalDate startAvailabilityDate) {
        this.startAvailabilityDate = startAvailabilityDate;
    }

    public LocalDate getEndAvailabilityDate() {
        return endAvailabilityDate;
    }

    public void setEndAvailabilityDate(LocalDate endAvailabilityDate) {
        this.endAvailabilityDate = endAvailabilityDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return horsepower == car.horsepower && pricePerDay == car.pricePerDay && available == car.available && Objects.equals(id, car.id) && Objects.equals(brand, car.brand) && Objects.equals(color, car.color) && Objects.equals(startAvailabilityDate, car.startAvailabilityDate) && Objects.equals(endAvailabilityDate, car.endAvailabilityDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, color, horsepower, pricePerDay, available, startAvailabilityDate, endAvailabilityDate);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                ", horsepower=" + horsepower +
                ", pricePerDay=" + pricePerDay +
                ", available=" + available +
                ", startAvailabilityDate=" + startAvailabilityDate +
                ", endAvailabilityDate=" + endAvailabilityDate +
                '}';
    }

}