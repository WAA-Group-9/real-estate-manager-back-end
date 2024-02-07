package org.waagroup9.realestatemanagement.service;

import org.springframework.stereotype.Service;
import org.waagroup9.realestatemanagement.config.CustomError;
import org.waagroup9.realestatemanagement.dto.OfferDTO;
import org.waagroup9.realestatemanagement.model.OfferStatus;
import org.waagroup9.realestatemanagement.model.entity.Offer;

import java.util.List;

@Service
public interface OfferService {
  OfferDTO createOffer(OfferDTO offer);
  void updateOffer(OfferDTO offer);
  Offer getOfferById(Long id);
  List<OfferDTO> getAllOffers();
  void respondToOffer(Long id, OfferStatus status);

  void acceptOffer(Long id);
  void rejectOffer(Long id);
  void deleteOffer(Long id);
}