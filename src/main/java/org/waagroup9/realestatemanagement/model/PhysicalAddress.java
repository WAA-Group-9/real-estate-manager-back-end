package org.waagroup9.realestatemanagement.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhysicalAddress {
    private String street;    private String city;
    private String zipCode;
    private String country;
}
