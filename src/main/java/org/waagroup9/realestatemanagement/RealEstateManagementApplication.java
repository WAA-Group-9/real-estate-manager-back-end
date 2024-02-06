package org.waagroup9.realestatemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.waagroup9.realestatemanagement")
public class RealEstateManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealEstateManagementApplication.class, args);
    }

}
