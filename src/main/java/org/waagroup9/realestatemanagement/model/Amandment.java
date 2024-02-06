package org.waagroup9.realestatemanagement.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Amandment {
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
    @OneToMany(mappedBy = "amendment")
    private List<Property> property;
}
