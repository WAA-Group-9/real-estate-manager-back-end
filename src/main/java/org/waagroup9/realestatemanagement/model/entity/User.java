package org.waagroup9.realestatemanagement.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.waagroup9.realestatemanagement.model.AuditData;
import org.waagroup9.realestatemanagement.model.PhysicalAddress;
import org.waagroup9.realestatemanagement.model.UserType;

@Entity
@Setter
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

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