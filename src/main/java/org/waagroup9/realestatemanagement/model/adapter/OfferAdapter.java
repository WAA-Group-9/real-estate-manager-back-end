package org.waagroup9.realestatemanagement.model.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.waagroup9.realestatemanagement.dto.OfferDTO;
import org.waagroup9.realestatemanagement.model.entity.Offer;
import org.waagroup9.realestatemanagement.model.entity.User;
import org.waagroup9.realestatemanagement.service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class OfferAdapter {

    private UserService userService;

    @Autowired
    public OfferAdapter(UserService userService) {
        this.userService = userService;
    }

    public Offer dtoToEntity(OfferDTO dto) {
        Offer offer = new Offer();
        offer.setId(dto.getId());
        offer.setPropertyId(Long.parseLong(dto.getPropertyId()));
        offer.setOfferAmount(Double.parseDouble(dto.getOfferPrice()));
        offer.setOfferStatus(dto.getOfferStatus());
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dto.getOfferDate());
            offer.setOfferDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        offer.setDescription(dto.getOfferDescription());

        User user = userService.findUserByEmail(dto.getEmail());
        offer.setUser(user);

        return offer;
    }

    public OfferDTO entityToDto(Offer offer) {
        OfferDTO dto = new OfferDTO();
        dto.setId(offer.getId());
        dto.setEmail(offer.getUser().getEmail());
        dto.setPropertyId(offer.getPropertyId().toString());
        dto.setOfferPrice(offer.getOfferAmount().toString());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        dto.setOfferDate(formatter.format(offer.getOfferDate()));
        dto.setOfferStatus(offer.getOfferStatus());
        dto.setOfferDescription(offer.getDescription());

        return dto;
    }
}