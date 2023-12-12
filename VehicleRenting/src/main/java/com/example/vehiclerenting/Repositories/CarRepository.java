package com.example.vehiclerenting.Repositories;

import com.example.vehiclerenting.Models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT c FROM Car c WHERE c.available = true AND " +
            "(c.startAvailabilityDate IS NULL OR c.endAvailabilityDate IS NULL OR (:startDate <= c.endAvailabilityDate AND :endDate >= c.startAvailabilityDate))")
    Iterable<Car> findAvailableCarsBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}


