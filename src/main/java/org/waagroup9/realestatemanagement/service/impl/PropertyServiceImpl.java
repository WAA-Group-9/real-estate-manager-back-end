package org.waagroup9.realestatemanagement.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.waagroup9.realestatemanagement.dto.PropertyDTO;
import org.waagroup9.realestatemanagement.model.Property;
import org.waagroup9.realestatemanagement.repo.PropertyRepo;
import org.waagroup9.realestatemanagement.service.PropertyService;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private PropertyRepo propertyRepository;

    @Override
    public List<PropertyDTO> getAllProperties() {
        List<Property> properties = propertyRepository.findAll();
        return properties.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PropertyDTO getPropertyById(Long propertyId) {
        Optional<Property> propertyOptional = propertyRepository.findById(propertyId);
        return propertyOptional.map(this::convertToDto).orElse(null);
    }

    @Override
    public PropertyDTO createProperty(PropertyDTO propertyDTO) {
        Property property = convertToEntity(propertyDTO);
        Property savedProperty = propertyRepository.save(property);
        return convertToDto(savedProperty);
    }

    @Override
    public PropertyDTO updateProperty(Long propertyId, PropertyDTO propertyDTO) {
        Optional<Property> propertyOptional = propertyRepository.findById(propertyId);
        if (propertyOptional.isPresent()) {
            Property existingProperty = propertyOptional.get();
            updateEntityFromDto(existingProperty, propertyDTO);
            Property updatedProperty = propertyRepository.save(existingProperty);
            return convertToDto(updatedProperty);
        }
        return null;
    }

    @Override
    public void deleteProperty(Long propertyId) {
        propertyRepository.deleteById(propertyId);
    }

    private PropertyDTO convertToDto(Property property) {
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setId(property.getId());
        propertyDTO.setTitle(property.getTitle());
        propertyDTO.setDescription(property.getDescription());
        propertyDTO.setPropertyType(property.getPropertyType());
        propertyDTO.setPropertyStatus(property.getPropertyStatus());
        propertyDTO.setBedrooms(property.getBedrooms());
        propertyDTO.setTotalArea(property.getTotalArea());
        propertyDTO.setLotSize(property.getLotSize());
        propertyDTO.setPrice(property.getPrice());
        propertyDTO.setListingDate(property.getListingDate());
        return propertyDTO;
    }

    private Property convertToEntity(PropertyDTO propertyDTO) {
        Property property = new Property();
        property.setId(propertyDTO.getId());
        property.setTitle(propertyDTO.getTitle());
        property.setDescription(propertyDTO.getDescription());
        property.setPropertyType(propertyDTO.getPropertyType());
        property.setPropertyStatus(propertyDTO.getPropertyStatus());
        property.setBedrooms(propertyDTO.getBedrooms());
        property.setTotalArea(propertyDTO.getTotalArea());
        property.setLotSize(propertyDTO.getLotSize());
        property.setPrice(propertyDTO.getPrice());
        property.setListingDate(propertyDTO.getListingDate());
        return property;
    }

    private void updateEntityFromDto(Property property, PropertyDTO propertyDTO) {
        property.setId(propertyDTO.getId());
        property.setTitle(propertyDTO.getTitle());
        property.setDescription(propertyDTO.getDescription());
        property.setPropertyType(propertyDTO.getPropertyType());
        property.setPropertyStatus(propertyDTO.getPropertyStatus());
        property.setBedrooms(propertyDTO.getBedrooms());
        property.setTotalArea(propertyDTO.getTotalArea());
        property.setLotSize(propertyDTO.getLotSize());
        property.setPrice(propertyDTO.getPrice());
        property.setListingDate(propertyDTO.getListingDate());
    }
}
