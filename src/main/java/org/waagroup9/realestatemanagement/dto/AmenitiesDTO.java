package org.waagroup9.realestatemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmenitiesDTO {
    private boolean hasSwimmingPool;
    private boolean hasGym;
    private boolean hasParking;
    private boolean hasSecuritySystem;
    private boolean hasBalcony;
    private boolean hasFireplace;
    private boolean hasAirConditioning;
    private boolean hasCentralHeating;
    private boolean hasInternet;
    private boolean hasGarden;
    private boolean hasPool;
    private boolean hasElevator;
    private boolean hasGarage;
}