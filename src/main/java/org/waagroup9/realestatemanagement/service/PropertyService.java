package org.waagroup9.realestatemanagement.service;

import java.util.List;

import org.waagroup9.realestatemanagement.dto.PropertyDTO;

public interface PropertyService {
    List<PropertyDTO> getAllProperties();

    PropertyDTO getPropertyById(Long propertyId);

    PropertyDTO createProperty(PropertyDTO propertyDTO);

    PropertyDTO updateProperty(Long propertyId, PropertyDTO propertyDTO);

    void deleteProperty(Long propertyId);
}
