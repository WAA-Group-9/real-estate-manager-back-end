package org.waagroup9.realestatemanagement.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.waagroup9.realestatemanagement.model.Address;
import org.waagroup9.realestatemanagement.model.Amenities;
import org.waagroup9.realestatemanagement.model.PropertyType;
import org.waagroup9.realestatemanagement.model.PropertyStatus;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @Enumerated(EnumType.STRING)
    private PropertyStatus propertyStatus;

    private Date listingDate;

    @Embedded
    private Address address;
    private int bedrooms;
    private double totalArea;
    private double lotSize;

    @Embedded
    private Amenities amenities;

    private double price;

    private String currency;

    @OneToMany(mappedBy = "property", cascade = CascadeType.PERSIST)
    private List<PriceHistory> priceHistory;

    @OneToMany(mappedBy = "property", cascade = CascadeType.PERSIST)
    private List<PropertyImage> images;

    @ManyToOne
    private User owner;
}