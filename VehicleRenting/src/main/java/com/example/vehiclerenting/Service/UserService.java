package com.example.vehiclerenting.Service;

import com.example.vehiclerenting.Models.User;
import com.example.vehiclerenting.Repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User registerUser(String name, String password, Integer balance){
        if (name == null || password == null || balance == null){
            return null;
        } else {
            User user = new User();
            user.setName(name);
            user.setPassword(password);
            user.setBalance(balance);
            return userRepository.save(user);
        }
    }

    public User authenticate(String name, String password){
        return userRepository.findUsersByNameAndPassword(name, password).orElse(null);
    }



}
