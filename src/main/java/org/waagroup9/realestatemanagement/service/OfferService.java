package org.waagroup9.realestatemanagement.service;

import org.springframework.stereotype.Service;
import org.waagroup9.realestatemanagement.dto.OfferDTO;
import org.waagroup9.realestatemanagement.model.OfferStatus;
import org.waagroup9.realestatemanagement.model.entity.Offer;

import java.util.List;

@Service
public interface OfferService {
  void createOffer(OfferDTO offer);
  void updateOffer(OfferDTO offer);
  void deleteOffer(Offer offer);
  Offer getOfferById(Long id);
  List<OfferDTO> getAllOffers();
  void sendOffer(OfferDTO offer);

  void respondToOffer(Long id, OfferStatus status);
    void deleteOffer(Long id);
}