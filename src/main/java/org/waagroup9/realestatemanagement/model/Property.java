package org.waagroup9.realestatemanagement.model;

import java.util.List;

import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @ManyToOne()
    @JoinColumn(name = "amandment_id")
    private Amandment amenities;
    @Enumerated(EnumType.STRING)
    private User owner;
    @Lob
    private List<byte[]> propertyPhotos;
}
