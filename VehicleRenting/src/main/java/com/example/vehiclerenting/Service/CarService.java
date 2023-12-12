package com.example.vehiclerenting.Service;

import com.example.vehiclerenting.Models.Car;
import com.example.vehiclerenting.Repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
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

}
