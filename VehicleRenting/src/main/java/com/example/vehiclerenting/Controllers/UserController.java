package com.example.vehiclerenting.Controllers;

import org.springframework.ui.Model;
import com.example.vehiclerenting.Models.Car;
import com.example.vehiclerenting.Models.Motorcycle;
import com.example.vehiclerenting.Models.User;
import com.example.vehiclerenting.Repositories.UserRepository;
import com.example.vehiclerenting.Service.CarService;
import com.example.vehiclerenting.Service.MotorcycleService;
import com.example.vehiclerenting.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final CarService carService;

    private final MotorcycleService motorcycleService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService, CarService carService, MotorcycleService motorcycleService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.carService = carService;
        this.motorcycleService = motorcycleService;
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register_page";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        User registeredUser = userService.registerUser(username, password);
        if (registeredUser != null) {
            return "redirect:/renting";
        } else {
            return "redirect:/error?errorMessage=Registration failed. Please try again.";
        }
    }

    // TODO: Errors should be like a pop-up with the error message


    @GetMapping("/login")
    public String getLoginPage() {
        return "login_page";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password) {
        User loggedInUser = userService.authenticate(username, password);
        if (loggedInUser != null) {
            return "redirect:/renting";
        } else {
            return "redirect:/error?errorMessage=Login failed. Please try again.";
        }
    }

    @GetMapping("/renting")
    public String getRentingPage(Model model) {
        Iterable<Car> cars = carService.getAllCars();
        Iterable<Motorcycle> motorcycles = motorcycleService.getAllMotorcycles();
        model.addAttribute("cars", cars);
        model.addAttribute("motorcycles", motorcycles);
        return "renting_page";
    }

    // Other endpoints and logic related to user authentication
    // ...
}
