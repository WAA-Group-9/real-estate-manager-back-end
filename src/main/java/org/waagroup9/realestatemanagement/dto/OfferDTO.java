package org.waagroup9.realestatemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.waagroup9.realestatemanagement.model.OfferStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferDTO {
    private Long id;
    private String email;
    private String propertyId;
    private String offerPrice;
    private String offerDate;
    private OfferStatus offerStatus = OfferStatus.PENDING;
    private String offerDescription;


}
