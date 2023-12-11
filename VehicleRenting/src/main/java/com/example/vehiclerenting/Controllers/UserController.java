package com.example.vehiclerenting.Controllers;

import com.example.vehiclerenting.Models.User;
import com.example.vehiclerenting.Repositories.UserRepository;
import com.example.vehiclerenting.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterPage(){
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
    @GetMapping("/error")
    public String exampleHandler(Model model, String errorMessage) {
        model.addAttribute("message", errorMessage);
        return "error_view";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login_page";
    }

    @GetMapping("/renting")
    public String getRentingPage(){
        return "renting_page";
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
}