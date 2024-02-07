package org.waagroup9.realestatemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.waagroup9.realestatemanagement.model.entity.Offer;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> getOfferByProperty_IdOrderByOfferDateDesc(Long id);
}