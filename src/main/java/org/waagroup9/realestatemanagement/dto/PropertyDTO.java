package org.waagroup9.realestatemanagement.dto;

import lombok.*;
import org.waagroup9.realestatemanagement.model.Address;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDTO {
    private String id;
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
    private List<PriceHistoryDTO> priceHistory;
    private List<PropertyImageDTO> images;
    private String owner;
}