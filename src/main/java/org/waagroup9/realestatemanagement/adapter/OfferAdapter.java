package org.waagroup9.realestatemanagement.adapter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.waagroup9.realestatemanagement.config.CustomError;
import org.waagroup9.realestatemanagement.dto.OfferDTO;
import org.waagroup9.realestatemanagement.model.entity.Offer;
import org.waagroup9.realestatemanagement.model.entity.Property;
import org.waagroup9.realestatemanagement.model.entity.User;
import org.waagroup9.realestatemanagement.service.PropertyService;
import org.waagroup9.realestatemanagement.service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class OfferAdapter {

    private final ModelMapper modelMapper;


    public Offer dtoToEntity(OfferDTO dto) {
        Offer offer = modelMapper.map(dto, Offer.class);
        return offer;
    }

    public OfferDTO entityToDto(Offer offer) {
      OfferDTO offerDTO = modelMapper.map(offer, OfferDTO.class);
      offerDTO.setPropertyId(offer.getProperty().getId());
      offerDTO.setEmail(offer.getUser().getEmail());
      return offerDTO;
    }
}