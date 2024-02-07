package org.waagroup9.realestatemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.waagroup9.realestatemanagement.config.CustomError;
import org.waagroup9.realestatemanagement.config.advice.annotations.CheckOwnerAccess;
import org.waagroup9.realestatemanagement.config.advice.annotations.CheckUserAccess;
import org.waagroup9.realestatemanagement.dto.OfferDTO;
import org.waagroup9.realestatemanagement.model.OfferStatus;
import org.waagroup9.realestatemanagement.model.entity.Offer;
import org.waagroup9.realestatemanagement.service.OfferService;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/offers")
public class OfferController {
    @Autowired
    private OfferService offerService;

    @PostMapping
    @CheckOwnerAccess
    public ResponseEntity<OfferDTO> createOffer(@RequestBody OfferDTO offer) throws CustomError {
        offerService.createOffer(offer);
        return new ResponseEntity<>(offer, HttpStatus.CREATED);
    }
    @PutMapping
    @CheckUserAccess
    public ResponseEntity<OfferDTO> updateOffer(@RequestBody OfferDTO OfferDTO) {
        offerService.updateOffer(OfferDTO);
        return new ResponseEntity<>(OfferDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @CheckUserAccess
    public ResponseEntity<Void> deleteOffer(@PathVariable Long id) {
        offerService.deleteOffer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @CheckUserAccess
    public ResponseEntity<Offer> getOfferById(@PathVariable Long id) {
        Offer offer = offerService.getOfferById(id);
        return new ResponseEntity<>(offer, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Offer>> getAllOffers() {
        List<Offer> offers = offerService.getAllOffers();
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @PostMapping("/send")
    public ResponseEntity<OfferDTO> sendOffer(@RequestBody OfferDTO offer) {
        offerService.sendOffer(offer);
        return new ResponseEntity<>(offer, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @CheckUserAccess
    public ResponseEntity<Void> respondToOffer(@PathVariable Long id, @RequestBody OfferStatus status) {
        offerService.respondToOffer(id, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/accept/{id}")
    @CheckUserAccess
    @CheckOwnerAccess
    public ResponseEntity<Void> acceptOffer(@PathVariable Long id) {
        offerService.acceptOffer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/reject/{id}")
    @CheckOwnerAccess
    @CheckUserAccess
    public ResponseEntity<Void> rejectOffer(@PathVariable Long id) {
        offerService.rejectOffer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @ExceptionHandler({ CustomError.class, UserPrincipalNotFoundException.class })
    public ResponseEntity<String> handleException(Exception e) {
        // Customize your error response here
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

}