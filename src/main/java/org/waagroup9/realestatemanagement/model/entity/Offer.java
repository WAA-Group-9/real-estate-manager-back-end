package org.waagroup9.realestatemanagement.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.waagroup9.realestatemanagement.model.AuditData;
import org.waagroup9.realestatemanagement.model.OfferStatus;

import java.util.Date;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long propertyId;
    private Double offerAmount;
    @Enumerated(EnumType.STRING)
    private OfferStatus offerStatus;
    private Date offerDate;
    private String description;
    @ManyToOne
    private User user;
    @Embedded
    private AuditData auditData = new AuditData();
}