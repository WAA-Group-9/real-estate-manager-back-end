package org.waagroup9.realestatemanagement.adapter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.waagroup9.realestatemanagement.config.CustomError;
import org.waagroup9.realestatemanagement.dto.PriceHistoryDTO;
import org.waagroup9.realestatemanagement.dto.PropertyDTO;
import org.waagroup9.realestatemanagement.dto.PropertyImageDTO;
import org.waagroup9.realestatemanagement.model.entity.PriceHistory;
import org.waagroup9.realestatemanagement.model.entity.Property;
import org.waagroup9.realestatemanagement.model.entity.PropertyImage;
import org.waagroup9.realestatemanagement.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PropertyAdapter {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    public Property dtoToEntity(PropertyDTO propertyDTO) throws CustomError {
        Property property = modelMapper.map(propertyDTO, Property.class);
        property.setOwner(userService.getUserByEmail(propertyDTO.getOwner()));
        List<PriceHistory> priceHistories = propertyDTO.getPriceHistory().stream().map(priceHistoryDTO -> {
            PriceHistory priceHistory = new PriceHistory();
            priceHistory.setPrice(priceHistoryDTO.getPrice());
            priceHistory.setDate(priceHistoryDTO.getDate());
            priceHistory.setProperty(property);
            return priceHistory;
        }).toList();
        property.setPriceHistory(priceHistories);

        List<PropertyImage> propertyImages = propertyDTO.getImages().stream().map(propertyImageDTO -> {
            PropertyImage propertyImage = new PropertyImage();
            propertyImage.setUrl(propertyImageDTO.getUrl());
            propertyImage.setProperty(property);
            return propertyImage;
        }).toList();

        property.setImages(propertyImages);
        return property;
    }

    public PropertyDTO entityToDto(Property property) {
        //TODO : refactor
        PropertyDTO propertyDTO = modelMapper.map(property, PropertyDTO.class);
        propertyDTO.setOwner(property.getOwner().getEmail());
        List<PriceHistoryDTO> priceHistoryDTOs = property.getPriceHistory().stream().map(priceHistory -> {
            PriceHistoryDTO priceHistoryDTO = new PriceHistoryDTO();
            priceHistoryDTO.setPrice(priceHistory.getPrice());
            priceHistoryDTO.setDate(priceHistory.getDate());
            return priceHistoryDTO;
        }).collect(Collectors.toList());
        propertyDTO.setPriceHistory(priceHistoryDTOs);

        List<PropertyImageDTO> propertyImageDTOS = property.getImages().stream().map(propertyImage -> modelMapper.map(propertyImage, PropertyImageDTO.class)
        ).collect(Collectors.toList());
        propertyDTO.setImages(propertyImageDTOS);

        return propertyDTO;
    }
}