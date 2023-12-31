package com.example.vehiclerenting.Service;

import com.example.vehiclerenting.Models.Rental;
import com.example.vehiclerenting.Models.User;
import com.example.vehiclerenting.Repositories.RentalRepository;
import com.example.vehiclerenting.Repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class UserService
{
    private final UserRepository userRepository;

    private final RentalRepository rentalRepository;
    private final EntityManager entityManager;

    public UserService(UserRepository userRepository, RentalRepository rentalRepository, EntityManager entityManager){
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
        this.entityManager = entityManager;
    }

    public boolean isUsernameAvailable(String username) {
        Optional<User> userOptional = userRepository.findUsersByName(username);
        return userOptional.isEmpty();
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

    @Transactional
    public boolean deleteUserByAttributes(String username, String password, Integer balance) {
        StringBuilder queryBuilder = new StringBuilder("DELETE FROM User m WHERE 1 = 1");

        // Append conditions based on the provided attributes
        if (username != null) {
            queryBuilder.append(" AND m.name = :username");
        }
        if (password != null) {
            queryBuilder.append(" AND m.password = :password");
        }
        if (balance != null) {
            queryBuilder.append(" AND m.balance = :balance");
        }
        Query query = entityManager.createQuery(queryBuilder.toString());

        // Set parameters for the conditions
        if (username != null) {
            query.setParameter("username", username);
        }
        if (password != null) {
            query.setParameter("password", password);
        }
        if (balance != null) {
            query.setParameter("balance",balance);
        }

        int deletedCount = query.executeUpdate();
        if (deletedCount == 1){
            return true;
        }
        return false;
    }



    @Transactional
    public void updateUserBalance(Long userId, int amount) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return;
        }

        int newBalance = user.getBalance() - amount;
        if (newBalance < 0) {
            return;
        }

        user.setBalance(newBalance);
        userRepository.save(user);
    }

    public List<Rental> getRentalHistory(Long userId) {
        return rentalRepository.findByUser_Id(userId);
    }




@Transactional
public void addBalance(Long userId, int amount)
{
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new NoSuchElementException("User not found."));

    if (amount < 0) {
        throw new IllegalArgumentException("Amount to add cannot be negative.");
    }

    user.setBalance(user.getBalance() + amount);
    userRepository.save(user);

}


    public int getUserBalance(Long userId) {

        return userRepository.findById(userId)

                .orElseThrow(() -> new NoSuchElementException("User not found."))
                .getBalance();

    }



}