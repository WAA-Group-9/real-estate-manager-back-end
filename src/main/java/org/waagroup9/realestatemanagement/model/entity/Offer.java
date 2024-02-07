package org.waagroup9.realestatemanagement.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.waagroup9.realestatemanagement.model.AuditData;
import org.waagroup9.realestatemanagement.model.OfferStatus;

import java.util.Date;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;
    private Double offerAmount;
    @Enumerated(EnumType.STRING)
    private OfferStatus offerStatus;
    private Date offerDate;
    private String description;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    @Embedded
    private AuditData auditData = new AuditData();
}