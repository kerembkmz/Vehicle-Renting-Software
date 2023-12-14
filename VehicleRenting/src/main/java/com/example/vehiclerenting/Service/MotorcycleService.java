package com.example.vehiclerenting.Service;

import com.example.vehiclerenting.Models.Car;
import com.example.vehiclerenting.Models.Motorcycle;
import com.example.vehiclerenting.Repositories.MotorcycleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MotorcycleService {

    private final MotorcycleRepository motorcycleRepository;
    private final EntityManager entityManager;

    @Autowired
    public MotorcycleService(MotorcycleRepository motorcycleRepository, EntityManager entityManager) {
        this.motorcycleRepository = motorcycleRepository;
        this.entityManager = entityManager;
    }

    public List<Motorcycle> getAllMotorcycles() {
        return motorcycleRepository.findAll();
    }

    public Optional<Motorcycle> getMotorcycleById(Long id) {
        return motorcycleRepository.findById(id);
    }

    public Motorcycle saveMotorcycle(Motorcycle motorcycle) {
        return motorcycleRepository.save(motorcycle);
    }

    public void deleteMotorcycleById(Long id) {
        motorcycleRepository.deleteById(id);
    }

    public Iterable<Motorcycle> findAvailableMotorcyclesByDateRange(LocalDate startDate, LocalDate endDate) {

        return motorcycleRepository.findAvailableMotorcyclesBetweenDates(startDate, endDate);
    }


    @Transactional
    public int deleteMotorcycleByAttributes(String brand, String color, Integer horsepower, Integer priceperday,
                                             Boolean available, LocalDate startAvailabilityDate, LocalDate endAvailabilityDate) {

        StringBuilder queryBuilder = new StringBuilder("DELETE FROM Motorcycle m WHERE 1 = 1");

        // Append conditions based on the provided attributes
        if (brand != null) {
            queryBuilder.append(" AND m.brand = :brand");
        }
        if (color != null) {
            queryBuilder.append(" AND m.color = :color");
        }
        if (horsepower != null) {
            queryBuilder.append(" AND m.horsepower = :horsepower");
        }
        if (priceperday != null) {
            queryBuilder.append(" AND m.pricePerDay = :priceperday");
        }
        if (available != null) {
            queryBuilder.append(" AND m.available = :available");
        }
        if (startAvailabilityDate != null) {
            queryBuilder.append(" AND m.startAvailabilityDate = :startAvailabilityDate");
        }
        if (endAvailabilityDate != null) {
            queryBuilder.append(" AND m.endAvailabilityDate = :endAvailabilityDate");
        }

        Query query = entityManager.createQuery(queryBuilder.toString());

        // Set parameters for the conditions
        if (brand != null) {
            query.setParameter("brand", brand);
        }
        if (color != null) {
            query.setParameter("color", color);
        }
        if (horsepower != null) {
            query.setParameter("horsepower", horsepower);
        }
        if (priceperday != null) {
            query.setParameter("priceperday", priceperday);
        }
        if (available != null) {
            query.setParameter("available", available);
        }
        if (startAvailabilityDate != null) {
            query.setParameter("startAvailabilityDate", startAvailabilityDate);
        }
        if (endAvailabilityDate != null) {
            query.setParameter("endAvailabilityDate", endAvailabilityDate);
        }

        int deletedCount = query.executeUpdate();
        return deletedCount;
    }
}
