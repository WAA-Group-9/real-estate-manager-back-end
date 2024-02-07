package org.waagroup9.realestatemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PriceHistoryDTO {
    private double price;
    private Date date;
    private String currency;
    private Long propertyId;
}