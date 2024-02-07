package org.waagroup9.realestatemanagement.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PriceHistoryDTO {
    private double price;
    private Date date;
    private String currency;
    private Long propertyId;
}