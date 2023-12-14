package com.example.vehiclerenting.Service;

import com.example.vehiclerenting.Models.User;
import com.example.vehiclerenting.Repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;



@Service
public class UserService
{
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    public UserService(UserRepository userRepository, EntityManager entityManager){
        this.userRepository = userRepository;
        this.entityManager = entityManager;
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
}
