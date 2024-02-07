package org.waagroup9.realestatemanagement.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.waagroup9.realestatemanagement.model.AuditData;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany
    @JoinTable(
        name = "mylist_property",
        joinColumns = @JoinColumn(name = "mylist_id"),
        inverseJoinColumns = @JoinColumn(name = "property_id"))
    private List<Property> properties;
    @Embedded
    private AuditData auditData = new AuditData();
}