package org.waagroup9.realestatemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.waagroup9.realestatemanagement.model.entity.Property;


@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
}