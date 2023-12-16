package com.example.vehiclerenting.Service;

import com.example.vehiclerenting.Models.Car;
import com.example.vehiclerenting.Repositories.CarRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final EntityManager entityManager;

    @Autowired
    public CarService(CarRepository carRepository , EntityManager entityManager) {
        this.carRepository = carRepository;
        this.entityManager = entityManager;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }

    public Iterable<Car> findAvailableCarsByDateRange(LocalDate startDate, LocalDate endDate) {

        return carRepository.findAvailableCarsBetweenDates(startDate, endDate);
    }

    @Transactional
    public int deleteCarByAttributes(String brand, String color, Integer horsepower, Integer priceperday,
                                            Boolean available, LocalDate startAvailabilityDate, LocalDate endAvailabilityDate) {

        StringBuilder queryBuilder = new StringBuilder("DELETE FROM Car m WHERE 1 = 1");

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

    public Iterable<Car> findAvailableCarsWithFilters(LocalDate startDate, LocalDate endDate, String brand, String color, Integer minHorsepower, Integer maxPricePerDay, Boolean availability) {
        List<Car> availableCars = (List<Car>) carRepository.findAvailableCarsBetweenDates(startDate, endDate);

        availableCars = availableCars.stream()
                .filter(car ->
                        (brand == null || brand.isEmpty() || car.getBrand().equalsIgnoreCase(brand)) &&
                                (color == null || color.isEmpty() || car.getColor().equalsIgnoreCase(color)) &&
                                (minHorsepower == null || car.getHorsepower() >= minHorsepower) &&
                                (maxPricePerDay == null || car.getPricePerDay() <= maxPricePerDay) &&
                                (availability == null || car.isAvailable() == availability))
                .collect(Collectors.toList());

        return availableCars;
    }

}
