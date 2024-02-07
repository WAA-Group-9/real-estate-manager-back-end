package org.waagroup9.realestatemanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.waagroup9.realestatemanagement.config.CustomError;
import org.waagroup9.realestatemanagement.dto.OfferDTO;
import org.waagroup9.realestatemanagement.dto.PropertyDTO;
import org.waagroup9.realestatemanagement.model.UserType;
import org.waagroup9.realestatemanagement.model.OfferStatus;
import org.waagroup9.realestatemanagement.adapter.OfferAdapter;
import org.waagroup9.realestatemanagement.model.entity.Offer;
import org.waagroup9.realestatemanagement.model.entity.Property;
import org.waagroup9.realestatemanagement.model.entity.User;
import org.waagroup9.realestatemanagement.repository.OfferRepository;
import org.waagroup9.realestatemanagement.repository.PropertyRepository;
import org.waagroup9.realestatemanagement.repository.UserRepository;
import org.waagroup9.realestatemanagement.service.OfferService;
import org.waagroup9.realestatemanagement.util.UserUtil;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final OfferAdapter offerAdapter;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final UserUtil userUtil;


    @Override
    public OfferDTO createOffer(OfferDTO offerDTO) {
        // TODO : centralize validation
        if (userUtil.getCurrentUserType() == UserType.BUYER) {
            throw new RuntimeException("Only buyers can create offers");
        }

        User user = userRepository.findUserByEmail(offerDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Property property = propertyRepository.findById(offerDTO.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        Offer offer = offerAdapter.dtoToEntity(offerDTO);
        offer.setProperty(property);
        offer.setUser(user);
        offer.setId(null);

        Offer savedOffer = offerRepository.save(offer);
        return offerAdapter.entityToDto(savedOffer);
    }

    @Override
    public void updateOffer(OfferDTO offer) {
        offerRepository.save(offerAdapter.dtoToEntity(offer));
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

    //TODO : check if we still need it
    @Override
    public void respondToOffer(Long id, OfferStatus status) {
        Optional<Offer> offer = offerRepository.findById(id);
        if (offer.isPresent()) {
            offer.get().setOfferStatus(status);
            offerRepository.save(offer.get());
        }
    }

    @Override
    public void acceptOffer(Long id) {
        Offer offer = validateOfferOwnership(id);

        // If the user is authorized, accept the offer
        offer.setOfferStatus(OfferStatus.ACCEPTED);

        // TODO : notify the buyer
        offerRepository.save(offer);
    }

    @Override
    public void rejectOffer(Long id) {
        Offer offer = validateOfferOwnership(id);

        // If the user is authorized, reject the offer
        offer.setOfferStatus(OfferStatus.REJECTED);
        offerRepository.save(offer);
    }

    private Offer validateOfferOwnership(Long id) {
        // Check if the current user is a buyer
        if (userUtil.getCurrentUserType() == UserType.BUYER) {
            throw new RuntimeException("Only sellers can accept or reject offers");
        }

        // Retrieve the offer from the repository
        Optional<Offer> optionalOffer = offerRepository.findById(id);
        if (optionalOffer.isEmpty()) {
            throw new RuntimeException("Offer not found");
        }

        // Get the offer and the owner's email
        Offer offer = optionalOffer.get();
        String ownerEmail = offer.getProperty().getOwner().getEmail();

        // Get the authenticated user's email
        String authenticatedUserEmail = userUtil.getEmailFromAuthentication();

        // Check if the authenticated user is the owner of the property
        if (!ownerEmail.equals(authenticatedUserEmail)) {
            throw new RuntimeException("User not authorized to accept or reject offer");
        }

        return offer;
    }

    @Override
    public void deleteOffer(Long id) {
        // TODO : check if user is owner or (check if the customer who created the offer and if offer is not contingent)
        offerRepository.deleteById(id);
    }
}