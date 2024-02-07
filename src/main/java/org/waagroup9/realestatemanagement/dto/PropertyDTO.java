package org.waagroup9.realestatemanagement.dto;

import org.waagroup9.realestatemanagement.model.Address;
import org.waagroup9.realestatemanagement.model.PropertyStatus;
import org.waagroup9.realestatemanagement.model.PropertyType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDTO {
    private String title;
    private String description;
    private String propertyType;
    private String propertyStatus;
    private String listingDate;
    private Address address;
    private int bedrooms;
    private double totalArea;
    private double lotSize;
    private AmenitiesDTO amenities;
    private long price;
    private String currency;
    private List<PriceHistoryDTO> priceHistoryDTOList;
    private UserDTO user;
}