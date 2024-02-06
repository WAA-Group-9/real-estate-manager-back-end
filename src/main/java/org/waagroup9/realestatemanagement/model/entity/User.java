package org.waagroup9.realestatemanagement.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.waagroup9.realestatemanagement.model.AuditData;
import org.waagroup9.realestatemanagement.model.PhysicalAddress;
import org.waagroup9.realestatemanagement.model.UserType;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Enumerated
    private UserType userType;

    private String userName;

    private String phoneNumber;

    @Embedded
    private PhysicalAddress physicalAddress;

    private AuditData auditData;

    @Column(name = "password")
    private String password;

    private boolean active=false;

}