package org.waagroup9.realestatemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.waagroup9.realestatemanagement.dto.OfferDTO;
import org.waagroup9.realestatemanagement.dto.PropertyDTO;
import org.waagroup9.realestatemanagement.model.PropertyStatus;
import org.waagroup9.realestatemanagement.model.PropertyType;
import org.waagroup9.realestatemanagement.service.OfferService;
import org.waagroup9.realestatemanagement.service.PropertyService;

@RestController
@RequestMapping("/api/v1/properties")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PropertyController {
    @Autowired
    private PropertyService propertyService;

    @Autowired
    private OfferService offerService;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping
    public ResponseEntity<List<PropertyDTO>> getAllProperties(
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "minBedrooms", required = false) Integer minBedrooms,
            @RequestParam(value = "propertyType", required = false) PropertyType propertyType,
            @RequestParam(value = "propertyStatus", required = false) PropertyStatus propertyStatus) {
        List<PropertyDTO> properties;

        if (minPrice != null || maxPrice != null || minBedrooms != null || propertyType != null
                || propertyStatus != null) {
            properties = propertyService.findPropertiesByCriteria(minPrice, maxPrice, minBedrooms, propertyType,
                    propertyStatus);
        } else {
            properties = propertyService.getAllProperties();
        }

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

    @GetMapping("/{id}/offers")
    public ResponseEntity<List<OfferDTO>> getPropertyOffers(@PathVariable Long id) {
        List<OfferDTO> offers = propertyService.getPropertyOffers(id);
        return ResponseEntity.ok(offers);
    }


    @PostMapping("/{id}/offers")
    public ResponseEntity<OfferDTO> addOfferToProperty(@PathVariable Long id, @RequestBody OfferDTO offerDTO) {
        offerDTO.setPropertyId(id);
        OfferDTO createdOffer = offerService.createOffer(offerDTO);
        return new ResponseEntity<>(createdOffer, HttpStatus.CREATED);
    }

    @PatchMapping("/offer/{id}/accept")
    public ResponseEntity<Void> acceptOffer(@PathVariable Long id) {
        offerService.acceptOffer(id);
        return new ResponseEntity<>(HttpStatus.OK);
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

}