package org.waagroup9.realestatemanagement.model;

import jakarta.persistence.Entity;

@Entity
public enum PropertyType {
    HOUSE,
    APARTMENT,
    CONDO,
}
