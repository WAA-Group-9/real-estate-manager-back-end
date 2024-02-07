package org.waagroup9.realestatemanagement.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.waagroup9.realestatemanagement.dto.PropertyDTO;
import org.waagroup9.realestatemanagement.model.Address;
import org.waagroup9.realestatemanagement.model.PropertyStatus;
import org.waagroup9.realestatemanagement.model.PropertyType;
import org.waagroup9.realestatemanagement.model.entity.Property;
import org.waagroup9.realestatemanagement.repository.PropertyRepo;
import org.waagroup9.realestatemanagement.service.PropertyService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private PropertyRepo propertyRepository;

    @Autowired
    private EntityManager entityManager;

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

    public List<PropertyDTO> getPropertiesByPriceRange(double minPrice, double maxPrice) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Property> criteriaQuery = criteriaBuilder.createQuery(Property.class);
        Root<Property> root = criteriaQuery.from(Property.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.between(root.get("price"), minPrice, maxPrice));

        criteriaQuery.select(root).where(predicates.toArray(new Predicate[0]));

        List<Property> properties = entityManager.createQuery(criteriaQuery).getResultList();

        List<PropertyDTO> propertyDTOs = new ArrayList<>();
        for (Property property : properties) {
            propertyDTOs.add(convertToDto(property));
        }
        return propertyDTOs;
    }

    public List<PropertyDTO> findPropertiesByCriteria(Double minPrice, Double maxPrice, Integer minBedrooms,
            PropertyType propertyType, PropertyStatus propertyStatus) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Property> criteriaQuery = criteriaBuilder.createQuery(Property.class);
        Root<Property> root = criteriaQuery.from(Property.class);

        List<Predicate> predicates = new ArrayList<>();

        if (minPrice != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
        }

        if (maxPrice != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
        }

        if (minBedrooms != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("bedrooms"), minBedrooms));
        }

        if (propertyType != null) {
            predicates.add(criteriaBuilder.equal(root.get("propertyType"), propertyType));
        }

        if (propertyStatus != null) {
            predicates.add(criteriaBuilder.equal(root.get("propertyStatus"), propertyStatus));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        List<Property> properties = entityManager.createQuery(criteriaQuery).getResultList();

        return properties.stream().map(this::convertToDto).collect(Collectors.toList());
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
        propertyDTO.setAddress(new Address(property.getAddress().getCity(), property.getAddress().getStreet(),
                property.getAddress().getState(), property.getAddress().getZipCode(),
                property.getAddress().getCountry()));
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
        property.setAddress(new Address(propertyDTO.getAddress().getCity(),
                propertyDTO.getAddress().getStreet(), propertyDTO.getAddress().getState(),
                propertyDTO.getAddress().getZipCode(),
                propertyDTO.getAddress().getCountry()));
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
        property.setAddress(new Address(propertyDTO.getAddress().getCity(), propertyDTO.getAddress().getStreet(),
                propertyDTO.getAddress().getState(), propertyDTO.getAddress().getZipCode(),
                propertyDTO.getAddress().getCountry()));
    }
}