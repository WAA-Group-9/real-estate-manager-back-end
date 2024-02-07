package org.waagroup9.realestatemanagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.waagroup9.realestatemanagement.config.CustomError;
import org.waagroup9.realestatemanagement.dto.OfferDTO;
import org.waagroup9.realestatemanagement.model.OfferStatus;
import org.waagroup9.realestatemanagement.model.adapter.OfferAdapter;
import org.waagroup9.realestatemanagement.model.entity.Offer;
import org.waagroup9.realestatemanagement.repository.OfferRepository;
import org.waagroup9.realestatemanagement.service.OfferService;

import java.util.List;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    private final OfferRepository offerRepository;


    @Autowired
    private final OfferAdapter offerAdapter;

    public OfferServiceImpl(OfferAdapter offerAdapter, OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
        this.offerAdapter = offerAdapter;
    }

    @Override
    public void createOffer(OfferDTO offer) {
        offerRepository.save(offerAdapter.dtoToEntity(offer));
    }

    @Override
    public void updateOffer(OfferDTO offer) {

        offerRepository.save(offerAdapter.dtoToEntity(offer));
    }

    @Override
    public void deleteOffer(Offer offer) {
        offerRepository.delete(offer);
    }

    @Override
    public Offer getOfferById(Long id) {
        Optional<Offer> offer = offerRepository.findById(id);
        return offer.orElse(null);
    }

    @Override
    public List<OfferDTO> getAllOffers() {
        return offerRepository.findAll().stream().map(offerAdapter::entityToDto).toList();
    }

    @Override
    public void sendOffer(OfferDTO offer) {
        offer.setOfferStatus(OfferStatus.PENDING);
        offerRepository.save(offerAdapter.dtoToEntity(offer));
    }

    @Override
    public void respondToOffer(Long id, OfferStatus status) {
        Optional<Offer> offer = offerRepository.findById(id);
        if (offer.isPresent()) {
            offer.get().setOfferStatus(status);
            offerRepository.save(offer.get());
        }
    }

    @Override
    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }
}