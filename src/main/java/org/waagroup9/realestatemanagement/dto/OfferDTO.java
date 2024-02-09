package org.waagroup9.realestatemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.waagroup9.realestatemanagement.model.OfferStatus;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferDTO {
    private Long id;
    private String email;
    private Long propertyId;
    private Double offerAmount;
    private String offerDate;
    private String offerStatus;
    private String offerDescription;
}
