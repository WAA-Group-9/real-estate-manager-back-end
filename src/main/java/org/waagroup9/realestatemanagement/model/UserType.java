package org.waagroup9.realestatemanagement.model;

import jakarta.persistence.Entity;

@Entity
public enum UserType {
    CUSTOMER,
    OWNER,
    ADMIN
}
