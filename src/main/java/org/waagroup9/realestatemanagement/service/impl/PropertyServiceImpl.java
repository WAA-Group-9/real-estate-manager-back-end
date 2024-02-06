package org.waagroup9.realestatemanagement.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PropertyDTO> getAllProperties() {
        List<Property> properties = propertyRepository.findAll();
        return properties.stream()
                .map(property -> modelMapper.map(property, PropertyDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PropertyDTO getPropertyById(Long propertyId) {
        Optional<Property> propertyOptional = propertyRepository.findById(propertyId);
        return propertyOptional.map(property -> modelMapper.map(property, PropertyDTO.class)).orElse(null);
    }

    @Override
    public PropertyDTO createProperty(PropertyDTO propertyDTO) {
        Property property = modelMapper.map(propertyDTO, Property.class);
        Property savedProperty = propertyRepository.save(property);
        return modelMapper.map(savedProperty, PropertyDTO.class);
    }

    @Override
    public PropertyDTO updateProperty(Long propertyId, PropertyDTO propertyDTO) {
        Optional<Property> propertyOptional = propertyRepository.findById(propertyId);
        if (propertyOptional.isPresent()) {
            Property existingProperty = propertyOptional.get();
            modelMapper.map(propertyDTO, existingProperty);
            Property updatedProperty = propertyRepository.save(existingProperty);
            return modelMapper.map(updatedProperty, PropertyDTO.class);
        }
        return null;
    }

    @Override
    public void deleteProperty(Long propertyId) {
        propertyRepository.deleteById(propertyId);
    }
}
