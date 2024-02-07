package org.waagroup9.realestatemanagement.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.waagroup9.realestatemanagement.model.entity.Property;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Amenities {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
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

    @OneToOne
    private Property property;
}