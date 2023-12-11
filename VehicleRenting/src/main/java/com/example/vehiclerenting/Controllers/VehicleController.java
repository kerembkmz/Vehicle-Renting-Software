package com.example.vehiclerenting.Controllers;

import com.example.vehiclerenting.Models.Car;
import com.example.vehiclerenting.Models.Motorcycle;
import com.example.vehiclerenting.Service.CarService;
import com.example.vehiclerenting.Service.MotorcycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

//    @GetMapping("/renting_page")
//    public String getRentingPage(Model model) {
//        // Fetch both cars and motorcycles from the service
//        Iterable<Car> cars = carService.getAllCars();
//        Iterable<Motorcycle> motorcycles = motorcycleService.getAllMotorcycles();
//
//        // Add both collections to the model
//        model.addAttribute("cars", cars);
//        model.addAttribute("motorcycles", motorcycles);
//
//        // Return the Thymeleaf template for the renting page
//        return "renting_page";
//    }

    // Other endpoints and logic related to renting
}