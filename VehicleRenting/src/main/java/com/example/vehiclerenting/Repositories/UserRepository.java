package com.example.vehiclerenting.Repositories;

import com.example.vehiclerenting.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    Optional<User> findById(Long aLong);

    Optional<User> findUsersByNameAndPassword(String name, String password);

    Optional<User> findUsersByName(String name);


}