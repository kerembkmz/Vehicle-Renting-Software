package com.example.vehiclerenting.Repositories;

import com.example.vehiclerenting.Models.Car;
import com.example.vehiclerenting.Models.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {
    @Query("SELECT c FROM Motorcycle c WHERE c.available = true AND " +
            "(c.startAvailabilityDate IS NULL OR c.endAvailabilityDate IS NULL OR (:startDate <= c.endAvailabilityDate AND :endDate >= c.startAvailabilityDate))")
    Iterable<Motorcycle> findAvailableMotorcyclesBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
