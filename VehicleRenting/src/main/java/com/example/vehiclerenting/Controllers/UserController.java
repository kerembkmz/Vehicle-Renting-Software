package com.example.vehiclerenting.Controllers;

import com.example.vehiclerenting.Models.Rental;
import com.example.vehiclerenting.Repositories.RentalRepository;
import com.example.vehiclerenting.Service.RentalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;
    private final UserService userService;
    private final CarService carService;
    private final MotorcycleService motorcycleService;
    private final RentalService rentalService;

    @Autowired
    public UserController(UserRepository userRepository, RentalRepository rentalRepository, UserService userService, CarService carService, MotorcycleService motorcycleService, RentalService rentalService) {
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
        this.userService = userService;
        this.carService = carService;
        this.motorcycleService = motorcycleService;
        this.rentalService = rentalService;
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register_page";
    }

@PostMapping("/checkUsernameAvailability")
public ResponseEntity<String> checkUsernameAvailability(@RequestParam String username) {
    if (userService.isUsernameAvailable(username)) {
        return ResponseEntity.ok("available");
    } else {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("exists");
    }
}

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        int defaultBalance = 0;
        boolean isUsernameAvailable = userService.isUsernameAvailable(username);

        if (!isUsernameAvailable) {
            return "redirect:/register?error=UsernameAlreadyInUse";
        }


        User registeredUser = userService.registerUser(username, password, defaultBalance);
        if (registeredUser != null) {
            return "redirect:/login";
        } else {
            return "redirect:/error?errorMessage=Registration failed. Please try again.";
        }
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login_page";
    }

    @GetMapping("/adminPage")
    public String getAdminPage() {return "admin_page"; }


    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttrs, HttpSession session) {
        User loggedInUser = userService.authenticate(username, password);
        if (loggedInUser != null) {
            session.setAttribute("userId", loggedInUser.getId());

            if ("admin".equals(loggedInUser.getName())) {
                return "redirect:/adminPage";
            } else {
                redirectAttrs.addAttribute("userId", loggedInUser.getId());
                return "redirect:/renting";
            }
        } else {
            return "redirect:/login?error=WrongCredentials";
        }
    }

    @GetMapping("/renting")
    public String getRentingPage(Model model, HttpSession session) {

        Long id = (Long) session.getAttribute("userId");
        Optional<User> loggedInUser = userRepository.findById(id);
        if (loggedInUser.isPresent()) {
            Iterable<Car> cars = carService.getAllCars();
            Iterable<Motorcycle> motorcycles = motorcycleService.getAllMotorcycles();
            model.addAttribute("userBalance", loggedInUser.get().getBalance());
            model.addAttribute("cars", cars);
            model.addAttribute("motorcycles", motorcycles);
            return "renting_page";
        } else {
            return "redirect:/error?errorMessage=User not found";
        }
    }

    @PostMapping("/admin/motorcycles/create")
    public String createMotorcycle(@RequestParam String brand,
                                   @RequestParam String color,
                                   @RequestParam Integer horsepower,
                                   @RequestParam Integer priceperday,
                                   @RequestParam Boolean available,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startAvailabilityDate,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endAvailabilityDate) {



        Motorcycle motorcycle = new Motorcycle();
        motorcycle.setBrand(brand);
        motorcycle.setColor(color);
        motorcycle.setHorsepower(horsepower);
        motorcycle.setPricePerDay(priceperday);
        motorcycle.setAvailable(available);
        motorcycle.setStartAvailabilityDate(startAvailabilityDate);
        motorcycle.setEndAvailabilityDate(endAvailabilityDate);

        Motorcycle savedMotorcycle = motorcycleService.saveMotorcycle(motorcycle);
        if (savedMotorcycle != null){
            return "redirect:/adminPage?motorcycleCreated=true";
        }
        else{
            return "redirect:/adminPage?motorcycleCreated=false";
        }

    }
    @PostMapping("/rentVehicle")
    public ModelAndView rentVehicle(
            @RequestParam("vehicleId") Long vehicleId,
            @RequestParam("vehicleType") String vehicleType,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            HttpSession session,
            Model model

    ) {
        ModelAndView modelAndView = new ModelAndView();


        Long id = (Long) session.getAttribute("userId");
        Optional<User> loggedInUser = userRepository.findById(id);

        modelAndView.addObject("userBalance", loggedInUser.get().getBalance());

        if (startDate.isAfter(endDate)) {
            modelAndView = new ModelAndView("renting_page");
            modelAndView.addObject("error", "Invalid rental date selection.");
            modelAndView.addObject("userBalance", loggedInUser.get().getBalance());
            return modelAndView;
        }

        Long userId = (Long) session.getAttribute("userId");
        User user =  userRepository.findById(userId).get();
        boolean rentalSuccess;
        Rental rental = new Rental(user);
        rental.setStartDate(startDate);
        rental.setEndDate(endDate);


        // Assuming you have a RentalService to manage rental operations
        if ("car".equals(vehicleType)) {
            rentalSuccess = rentalService.rentCar(vehicleId, userId, startDate, endDate);
            rental.setCar(carService.getCarById(vehicleId).get());
        } else if ("motorcycle".equals(vehicleType)) {
            rentalSuccess = rentalService.rentMotorcycle(vehicleId, userId, startDate, endDate);
            rental.setMotorcycle(motorcycleService.getMotorcycleById(vehicleId).get());
        } else {
            modelAndView.setViewName("renting_page");
            modelAndView.addObject("error", "Invalid vehicle type.");
            return modelAndView;
        }

        if (rentalSuccess) {
            // Persist the rental record if rental was successful
            rentalService.saveRental(rental);

            modelAndView.setViewName("rent_confirmation");
            modelAndView.addObject("message", "Vehicle has been successfully rented.");
            modelAndView.addObject("rental", rental);
        } else {
            modelAndView.setViewName("renting_page");
            modelAndView.addObject("error", "Rental process failed.");
        }

        modelAndView.addObject("userId", userId);
        return modelAndView;
    }
    @PostMapping("/admin/users/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam Integer balance) {

        User user = new User();
        user.setName(username);
        user.setPassword(password);
        user.setBalance(balance);

        User savedUser = userService.registerUser(username, password, balance);
        if (savedUser != null) {
            return "redirect:/adminPage?userCreated=true";
        } else {
            return "redirect:/adminPage?userCreated=false";
        }
    }


    @PostMapping("/admin/cars/create")
    public String createCar(@RequestParam String brand,
                            @RequestParam String color,
                            @RequestParam Integer horsepower,
                            @RequestParam Integer priceperday,
                            @RequestParam Boolean available,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startAvailabilityDate,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endAvailabilityDate) {



        Car car = new Car();
        car.setBrand(brand);
        car.setColor(color);
        car.setHorsepower(horsepower);
        car.setPricePerDay(priceperday);
        car.setAvailable(available);
        car.setStartAvailabilityDate(startAvailabilityDate);
        car.setEndAvailabilityDate(endAvailabilityDate);

        Car savedCar = carService.saveCar(car);
        if (savedCar != null){
            return "redirect:/adminPage?carCreated=true";
        }
        else{
            return "redirect:/adminPage?carCreated=false";
        }

    }

    @PostMapping("/admin/motorcycles/delete")
    public String deleteMotorcycleByAttributes(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Integer horsepower,
            @RequestParam(required = false) Integer priceperday,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startAvailabilityDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endAvailabilityDate) {


        int deletedMotorcycleNum = motorcycleService.deleteMotorcycleByAttributes(brand, color, horsepower, priceperday, available, startAvailabilityDate, endAvailabilityDate);
        if (deletedMotorcycleNum > 0){
            return "redirect:/adminPage?motorcycleDeleted=true";
        }else{
            return "redirect:/adminPage?motorcycleDeleted=false";
        }

    }
    @PostMapping("/admin/cars/delete")
    public String deleteCarByAttributes(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Integer horsepower,
            @RequestParam(required = false) Integer priceperday,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startAvailabilityDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endAvailabilityDate) {


        int deletedCarNum = carService.deleteCarByAttributes(brand, color, horsepower, priceperday, available, startAvailabilityDate, endAvailabilityDate);
        if (deletedCarNum > 0){
            return "redirect:/adminPage?motorcycleDeleted=true";
        }else{
            return "redirect:/adminPage?motorcycleDeleted=false";
        }

    }

    @PostMapping("/admin/users/delete")
    public String deleteUsersByAttributes(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) Integer balance) {

        boolean deletedUser = userService.deleteUserByAttributes(username, password, balance);

        if (deletedUser) {
            return "redirect:/adminPage?userDeleted=true";
        } else {
            return "redirect:/adminPage?userDeleted=false";
        }
    }

    @GetMapping("/search")
    public String searchAvailableCars(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "color", required = false) String color,
            @RequestParam(value = "minHorsepower", required = false) Integer minHorsepower,
            @RequestParam(value = "maxPricePerDay", required = false) Integer maxPricePerDay,
            @RequestParam(value = "availability", required = false) String availabilityParam,
            HttpSession session,
            Model model) {

        Boolean availability = null;
        if (availabilityParam != null && !availabilityParam.isEmpty()) {
            availability = Boolean.parseBoolean(availabilityParam); // Convert to Boolean
        }

        Long id = (Long) session.getAttribute("userId");
        Optional<User> loggedInUser = userRepository.findById(id);

        model.addAttribute("startDate", startDate.toString());
        model.addAttribute("endDate", endDate.toString());
        model.addAttribute("brand", brand.toString());
        model.addAttribute("color", color.toString());
        if (minHorsepower != null) {
            model.addAttribute("minHorsepower", minHorsepower.toString());
        }
        if (maxPricePerDay != null) {
            model.addAttribute("maxPricePerDay", maxPricePerDay.toString());
        }




        if (loggedInUser.isPresent()) {
            Iterable<Car> availableCars = carService.findAvailableCarsWithFilters(startDate, endDate, brand, color, minHorsepower, maxPricePerDay, availability);
            Iterable<Motorcycle> availableMotorcycles = motorcycleService.findAvailableMotorcyclesWithFilters(startDate, endDate, brand, color, minHorsepower, maxPricePerDay, availability);
            model.addAttribute("userBalance", loggedInUser.get().getBalance());
            model.addAttribute("availableCars", availableCars);
            model.addAttribute("availableMotors", availableMotorcycles);
            return "renting_page";
        } else {
            return "redirect:/error?errorMessage=User not found";
        }
    }

    @PostMapping("/removeFilters")
    public String removeFilters(HttpSession session, Model model) {
        session.removeAttribute("startDate");
        session.removeAttribute("endDate");
        session.removeAttribute("brand");
        session.removeAttribute("color");
        session.removeAttribute("minHorsepower");
        session.removeAttribute("maxPricePerDay");

        Long id = (Long) session.getAttribute("userId");
        Optional<User> loggedInUser = userRepository.findById(id);
        model.addAttribute("userBalance", loggedInUser.get().getBalance());
        return "renting_page";
    }

    @GetMapping("/rental_history")
    public String getRentalHistory(Model model, HttpSession session) {
        Long user_id = (Long) session.getAttribute("userId");

        List<Rental> rentalHistory = rentalRepository.findByUser_Id(user_id);

        model.addAttribute("rentalHistory", rentalHistory);

        return "rental_history";
    }


    @PostMapping("/addBalance")
    public String addBalance(HttpSession session, @RequestParam int amount, RedirectAttributes redirectAttrs) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            redirectAttrs.addFlashAttribute("error", "User not found.");
            return "redirect:/renting";
        }

        userService.addBalance(userId, amount);

        return "redirect:/renting";
    }

}