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
import org.waagroup9.realestatemanagement.dto.UserDTO;
import org.waagroup9.realestatemanagement.service.UserService;

@RestController
@RequestMapping("/api/v2/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int userId) {
        UserDTO userDTO = userService.getUserById(userId);
        if (userDTO != null) {
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int userId, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(userId, userDTO);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // @GetMapping("/{userId}/offers")
    // public ResponseEntity<?> getUserOffers(@PathVariable int userId) {
    // List<OfferDTO> userOffers = userService.getUserOffers(userId);
    // return new ResponseEntity<>("User's offers", HttpStatus.OK);
    // }

    // @GetMapping("/{userId}/wishlist")
    // public ResponseEntity<?> getUserWishlist(@PathVariable int userId) {
    // List<WishlistItemDTO> userWishlist = userService.getUserWishlist(userId);
    // return new ResponseEntity<>("User's wishlist", HttpStatus.OK);
    // }

    // @GetMapping("/{userId}/property")
    // public ResponseEntity<?> getUserPropertiesInLocation(@PathVariable int
    // userId, @RequestParam String located) {
    // List<PropertyDTO> userPropertiesInLocation =
    // userService.getUserPropertiesInLocation(userId, located);
    // return new ResponseEntity<>("User's properties in location " + located,
    // HttpStatus.OK);
    // }
}
