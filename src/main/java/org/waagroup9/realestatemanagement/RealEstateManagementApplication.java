package org.waagroup9.realestatemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("org.waagroup9.realestatemanagement.model.entity")
public class RealEstateManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealEstateManagementApplication.class, args);
    }

}