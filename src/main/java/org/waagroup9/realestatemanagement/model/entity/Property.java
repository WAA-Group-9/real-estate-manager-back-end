package org.waagroup9.realestatemanagement.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.waagroup9.realestatemanagement.model.Address;
import org.waagroup9.realestatemanagement.model.PropertyStatus;
import org.waagroup9.realestatemanagement.model.PropertyType;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String description;
    private String listingDate;
    private int bedrooms;
    private double totalArea;
    private double lotSize;
    private long price;
    private String currency;
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;
    @Enumerated(EnumType.STRING)
    private PropertyStatus propertyStatus;
    @Embedded
    private Address address;

    @OneToOne(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Amenities amenities;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}