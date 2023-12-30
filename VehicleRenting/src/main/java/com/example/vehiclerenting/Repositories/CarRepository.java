package com.example.vehiclerenting.Repositories;

import com.example.vehiclerenting.Models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT c FROM Car c WHERE c.available = true AND " +
            "(c.startAvailabilityDate IS NULL OR c.endAvailabilityDate IS NULL OR (:startDate <= c.endAvailabilityDate AND :endDate >= c.startAvailabilityDate))")
    Iterable<Car> findAvailableCarsBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT DISTINCT c.brand FROM Car c")
    List<String> findDistinctBrands();

    @Query("SELECT DISTINCT c.color FROM Car c")
    List<String> findDistinctColor();

    @Query("SELECT DISTINCT c.horsepower FROM Car c")
    List<String> findDistincthorsePower();

}




