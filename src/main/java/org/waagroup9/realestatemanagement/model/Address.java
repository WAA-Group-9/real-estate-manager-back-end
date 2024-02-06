package org.waagroup9.realestatemanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue
    private Long id;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    @OneToOne(mappedBy = "address")
    private Property property;
    @OneToOne(mappedBy = "address")
    private User user;
}
