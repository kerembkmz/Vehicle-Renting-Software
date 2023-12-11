package com.example.vehiclerenting.Models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "Motorcycle", schema = "VRS")
public class Motorcycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String color;
    private int horsepower;
    private int pricePerDay;
    private boolean available;

    private LocalDate startAvailabilityDate;

    private LocalDate endAvailabilityDate;


    public Long getId() {
        return id;
    }


    public String getBrand() {
        return brand;
    }


    public String getColor() {
        return color;
    }


    public int getHorsepower() {
        return horsepower;
    }


    public int getPricePerDay() {
        return pricePerDay;
    }


    public boolean isAvailable() {
        return available;
    }


    public LocalDate getStartAvailabilityDate() {
        return startAvailabilityDate;
    }


    public LocalDate getEndAvailabilityDate() {
        return endAvailabilityDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setStartAvailabilityDate(LocalDate startAvailabilityDate) {
        this.startAvailabilityDate = startAvailabilityDate;
    }

    public void setEndAvailabilityDate(LocalDate endAvailabilityDate) {
        this.endAvailabilityDate = endAvailabilityDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Motorcycle that = (Motorcycle) o;
        return horsepower == that.horsepower && pricePerDay == that.pricePerDay && available == that.available && Objects.equals(id, that.id) && Objects.equals(brand, that.brand) && Objects.equals(color, that.color) && Objects.equals(startAvailabilityDate, that.startAvailabilityDate) && Objects.equals(endAvailabilityDate, that.endAvailabilityDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, color, horsepower, pricePerDay, available, startAvailabilityDate, endAvailabilityDate);
    }

    @Override
    public String toString() {
        return "Motorcycle{" +
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

    // Implement setters, constructors, and other necessary methods
}
