package org.waagroup9.realestatemanagement.dto;

import org.waagroup9.realestatemanagement.model.Address;
import org.waagroup9.realestatemanagement.model.Amandment;
import org.waagroup9.realestatemanagement.model.PropertyStatus;
import org.waagroup9.realestatemanagement.model.PropertyType;
import org.waagroup9.realestatemanagement.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDTO {
    private long id;
    private String title;
    private String description;
    private PropertyType propertyType;
    private PropertyStatus propertyStatus;
    private String listingDate;
    private Address address;
    private int bedrooms;
    private double totalArea;
    private double lotSize;
    private Amandment amenities;
    private long price;
    private User user;
}
