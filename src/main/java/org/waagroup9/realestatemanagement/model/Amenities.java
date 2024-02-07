package org.waagroup9.realestatemanagement.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Amenities {
    private boolean hasPool;
    private boolean hasGarage;
    private boolean hasGarden;
    private boolean hasBalcony;

}