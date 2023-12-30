package com.example.vehiclerenting.Service;

import com.example.vehiclerenting.Models.Rental;
import com.example.vehiclerenting.Models.User;
import com.example.vehiclerenting.Repositories.RentalRepository;
import com.example.vehiclerenting.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.vehiclerenting.Models.Car;
import com.example.vehiclerenting.Models.Motorcycle;
import com.example.vehiclerenting.Repositories.CarRepository;
import com.example.vehiclerenting.Repositories.MotorcycleRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class RentalService {

    private final CarRepository carRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    @Autowired
    public RentalService(CarRepository carRepository, MotorcycleRepository motorcycleRepository, RentalRepository rentalRepository, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.motorcycleRepository = motorcycleRepository;
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
    }

    public boolean rentCar(Long carId, Long userId, LocalDate startDate, LocalDate endDate) {
        Car car = carRepository.findById(carId).orElse(null);
        if (car != null && car.isAvailable()) {
            int days = (int) ChronoUnit.DAYS.between(startDate, endDate);
            int totalCost = days * car.getPricePerDay();



            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                if (user.getBalance() >= totalCost) {
                    car.setAvailable(false);
                    carRepository.save(car);


                    user.setBalance(user.getBalance() - totalCost);
                    userRepository.save(user);

                    return true;
                } else {
                    // Insufficient balance, return false without modifying anything
                    return false;
                }
            }
        }
        return false;
    }

    public boolean rentMotorcycle(Long motorcycleId, Long userId, LocalDate startDate, LocalDate endDate) {
        Motorcycle motorcycle = motorcycleRepository.findById(motorcycleId).orElse(null);
        if (motorcycle != null && motorcycle.isAvailable()) {
            int days = (int) ChronoUnit.DAYS.between(startDate, endDate);
            int totalCost = days * motorcycle.getPricePerDay();

            User user = userRepository.findById(userId).orElse(null);
            if (user != null && user.getBalance() >= totalCost) {
                motorcycle.setAvailable(false);
                motorcycleRepository.save(motorcycle);

                user.setBalance(user.getBalance() - totalCost);
                userRepository.save(user);


                return true;
            }
        }
        return false;
    }

    public void saveRental(Rental rental) {
        rentalRepository.save(rental);
    }
}