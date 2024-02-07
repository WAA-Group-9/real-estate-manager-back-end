package org.waagroup9.realestatemanagement.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.waagroup9.realestatemanagement.model.AuditData;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne
    private Property property;

    @Embedded
    private AuditData auditData = new AuditData();
}