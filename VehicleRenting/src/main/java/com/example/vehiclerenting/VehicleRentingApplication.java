package com.example.vehiclerenting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.vehiclerenting.Models")
public class VehicleRentingApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehicleRentingApplication.class, args);
    }

}
