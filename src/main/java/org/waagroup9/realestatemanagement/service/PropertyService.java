package org.waagroup9.realestatemanagement.service;

import java.util.List;

import org.waagroup9.realestatemanagement.config.CustomError;
import org.waagroup9.realestatemanagement.dto.OfferDTO;
import org.waagroup9.realestatemanagement.dto.PropertyDTO;
import org.waagroup9.realestatemanagement.model.PropertyStatus;
import org.waagroup9.realestatemanagement.model.PropertyType;

public interface PropertyService {
    List<PropertyDTO> getAllProperties();

    PropertyDTO getPropertyById(Long propertyId);

    PropertyDTO createProperty(PropertyDTO propertyDTO);

    PropertyDTO updateProperty(Long propertyId, PropertyDTO propertyDTO);

    void deleteProperty(Long propertyId);

    List<PropertyDTO> findPropertiesByCriteria(Double minPrice, Double maxPrice, Integer minBedrooms,
            PropertyType propertyType, PropertyStatus propertyStatus);

    List<OfferDTO> getPropertyOffers(Long id);
}
 