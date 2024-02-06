package org.waagroup9.realestatemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.waagroup9.realestatemanagement.dto.PropertyDTO;
import org.waagroup9.realestatemanagement.service.PropertyService;

@RestController
@RequestMapping("/api/v2/property")
public class PropertyController {

    private final PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping
    public ResponseEntity<List<PropertyDTO>> getAllProperties() {
        List<PropertyDTO> properties = propertyService.getAllProperties();
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @GetMapping("/{propertyId}")
    public ResponseEntity<PropertyDTO> getPropertyById(@PathVariable Long propertyId) {
        PropertyDTO propertyDTO = propertyService.getPropertyById(propertyId);
        if (propertyDTO != null) {
            return new ResponseEntity<>(propertyDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<PropertyDTO> createProperty(@RequestBody PropertyDTO propertyDTO) {
        PropertyDTO createdProperty = propertyService.createProperty(propertyDTO);
        return new ResponseEntity<>(createdProperty, HttpStatus.CREATED);
    }

    @PutMapping("/{propertyId}")
    public ResponseEntity<PropertyDTO> updateProperty(@PathVariable Long propertyId,
            @RequestBody PropertyDTO propertyDTO) {
        PropertyDTO updatedProperty = propertyService.updateProperty(propertyId, propertyDTO);
        if (updatedProperty != null) {
            return new ResponseEntity<>(updatedProperty, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<?> deleteProperty(@PathVariable Long propertyId) {
        propertyService.deleteProperty(propertyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // @GetMapping("/{propertyId}/offers")
    // public ResponseEntity<?> getPropertyOffers(@PathVariable int propertyId) {
    // List<OfferDTO> offers = propertyService.getPropertyOffers(propertyId);
    // return new ResponseEntity<>(offers, HttpStatus.OK);
    // }

}
