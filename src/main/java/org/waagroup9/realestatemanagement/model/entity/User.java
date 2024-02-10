package org.waagroup9.realestatemanagement.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.waagroup9.realestatemanagement.model.AuditData;
import org.waagroup9.realestatemanagement.model.UserType;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
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

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(unique = true)
    private String userName;

    @Column(unique = true)
    private String phoneNumber;

    @Embedded
    private AuditData auditData = new AuditData();

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Offer> offers = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_favorite_properties",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "property_id"))
    private List<Property> favoriteProperties = new ArrayList<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Property> properties = new ArrayList<>();

    private boolean active;

}