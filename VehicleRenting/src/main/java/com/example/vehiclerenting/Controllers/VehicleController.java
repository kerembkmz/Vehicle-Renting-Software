package com.example.vehiclerenting.Controllers;

import com.example.vehiclerenting.Models.Car;
import com.example.vehiclerenting.Models.Motorcycle;
import com.example.vehiclerenting.Models.Rental;
import com.example.vehiclerenting.Service.CarService;
import com.example.vehiclerenting.Service.MotorcycleService;
import com.example.vehiclerenting.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.StreamSupport;

@Controller
public class VehicleController {

    private final CarService carService;
    private final MotorcycleService motorcycleService;

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    public VehicleController(CarService carService, MotorcycleService motorcycleService) {
        this.carService = carService;
        this.motorcycleService = motorcycleService;
    }

//    @PostMapping("/rentVehicle")
//    public ModelAndView rentVehicle(
//            @RequestParam("vehicleId") Long vehicleId,
//            @RequestParam("vehicleType") String vehicleType, // 'car' or 'motorcycle'
//            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
//            @RequestParam("userId") Long userId
//    ) {
//        ModelAndView modelAndView = new ModelAndView();
//
//        boolean rentalSuccess;
//        Rental rental = new Rental(); // Create a new rental object to hold the details
//        rental.setStartDate(startDate);
//        rental.setEndDate(endDate);
//        rental.setUserId(userId);
//
//        if ("car".equals(vehicleType)) {
//            rentalSuccess = carService.rentCar(vehicleId, startDate, endDate, userId);
//            rental.setCarId(vehicleId); // Assuming you have a method to set carId in Rental
//        } else if ("motorcycle".equals(vehicleType)) {
//            rentalSuccess = motorcycleService.rentMotorcycle(vehicleId, startDate, endDate, userId);
//            rental.setMotorcycleId(vehicleId); // Assuming you have a method to set motorcycleId in Rental
//        } else {
//            modelAndView.setViewName("renting_page");
//            modelAndView.addObject("error", "Invalid vehicle type.");
//            return modelAndView;
//        }
//
//        if (rentalSuccess) {
//            modelAndView.setViewName("rent_confirmation");
//            modelAndView.addObject("message", "Vehicle has been successfully rented.");
//            modelAndView.addObject("rentalDetails", rental); // Pass the rental object to the view
//        } else {
//            modelAndView.setViewName("renting_page");
//            modelAndView.addObject("error", "Rental process failed.");
//        }
//
//        return modelAndView;
//    }
//    @GetMapping("/rentingPage")
//    public String getRentingPage(Model model) {
//        // Fetch both cars and motorcycles from the service
//        Iterable<Car> cars = carService.getAllCars();
//        Iterable<Motorcycle> motorcycles = motorcycleService.getAllMotorcycles();
//        List<String> availableBrands = carService.getAvailableBrands();
//        List<String> availableColor = carService.getAvailableColor();
//        List<String> horsePower = carService.horsePower();
//
//        // Add both collections to the model
//        model.addAttribute("allCars", cars);
//        model.addAttribute("allMotorcycles", motorcycles);
//        model.addAttribute("availableBrands", availableBrands);
//        model.addAttribute("availableColor", availableColor);
//        model.addAttribute("horsePower", horsePower);
//
//
//        // Return the Thymeleaf template for the renting page
//        return "renting_page";
//    }
//    /*
//    @GetMapping("/renting_page")
//    public String getRentingPage(Model model) {
//        // Fetch both cars and motorcycles from the service
//        Iterable<Car> cars = carService.getAllCars();
//        Iterable<Motorcycle> motorcycles = motorcycleService.getAllMotorcycles();
//        List<String> availableBrands = carService.getAvailableBrands();
//        List<String> availableColor = carService.getAvailableColor();
//        List<String> horsePower = carService.horsePower();
//
//        // Add both collections to the model
//        model.addAttribute("allCars", cars);
//        model.addAttribute("allMotorcycles", motorcycles);
//        model.addAttribute("availableBrands", availableBrands);
//        model.addAttribute("availableColor", availableColor);
//        model.addAttribute("horsePower", horsePower);
//
//
//        // Return the Thymeleaf template for the renting page
//        return "renting_page";
//    }*/

}