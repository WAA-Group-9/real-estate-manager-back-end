package org.waagroup9.realestatemanagement.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.waagroup9.realestatemanagement.adapter.PropertyAdapter;
import org.waagroup9.realestatemanagement.config.CustomError;
import org.waagroup9.realestatemanagement.dto.PropertyDTO;
import org.waagroup9.realestatemanagement.model.PropertyStatus;
import org.waagroup9.realestatemanagement.model.PropertyType;
import org.waagroup9.realestatemanagement.model.entity.Property;
import org.waagroup9.realestatemanagement.repository.PropertyRepository;
import org.waagroup9.realestatemanagement.service.PropertyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PropertyAdapter propertyAdapter;

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<PropertyDTO> getAllProperties() {
        List<Property> properties = propertyRepository.findAll();
        return properties.stream()
                .map(property -> propertyAdapter.entityToDto(property))
                .collect(Collectors.toList());
    }

    @Override
    public PropertyDTO getPropertyById(Long propertyId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(()->new RuntimeException("cant find property"));
        return propertyAdapter.entityToDto(property);
    }

    @Override
    public PropertyDTO createProperty(PropertyDTO propertyDTO) {
        try {
            Property property = propertyAdapter.dtoToEntity(propertyDTO);
            Property savedProperty = propertyRepository.save(property);
            return propertyAdapter.entityToDto(savedProperty);
        } catch (CustomError e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PropertyDTO updateProperty(Long propertyId, PropertyDTO propertyDTO) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        modelMapper.map(propertyDTO, property);
        Property updatedProperty = propertyRepository.save(property);
        return propertyAdapter.entityToDto(updatedProperty);
    }


    @Override
    public void deleteProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        propertyRepository.deleteById(property.getId());
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

        return properties.stream()
                .map(property -> propertyAdapter.entityToDto(property))
                .toList();
    }

}