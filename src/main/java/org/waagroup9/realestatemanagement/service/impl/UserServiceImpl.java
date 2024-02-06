package org.waagroup9.realestatemanagement.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.waagroup9.realestatemanagement.dto.UserDTO;
import org.waagroup9.realestatemanagement.model.User;
import org.waagroup9.realestatemanagement.repo.UserRepo;
import org.waagroup9.realestatemanagement.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(int userId) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isPresent()) {
            return convertToDTO(optionalUser.get());
        } else {
            throw new RuntimeException("User not found");

        }
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        User savedUser = userRepo.save(user);
        return convertToDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(int userId, UserDTO userDTO) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setTelephone(userDTO.getTelephone());

            User updatedUser = userRepo.save(user);
            return convertToDTO(updatedUser);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public void deleteUser(int userId) {
        userRepo.deleteById(userId);
    }

    private UserDTO convertToDTO(User user) {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setTelephone(user.getTelephone());
        return userDTO;
    }

    private User convertToEntity(UserDTO userDTO) {

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setTelephone(userDTO.getTelephone());

        return user;
    }
    // public List<OfferDTO> getUserOffers(int userId) {
    // // Assuming you have a method in your repository to fetch user's offers by
    // user ID
    // List<Offer> offers = offerRepository.findByUserId(userId);
    // return modelMapper.map(offers, new TypeToken<List<OfferDTO>>() {}.getType());
    // }

    // public List<WishlistItemDTO> getUserWishlist(int userId) {
    // // Assuming you have a method in your repository to fetch user's wishlist by
    // user ID
    // List<WishlistItem> wishlistItems =
    // wishlistItemRepository.findByUserId(userId);
    // return modelMapper.map(wishlistItems, new TypeToken<List<WishlistItemDTO>>()
    // {}.getType());
    // }

    // public List<PropertyDTO> getUserPropertiesInLocation(int userId, String
    // located) {
    // // Assuming you have a method in your repository to fetch user's properties
    // in a specific location by user ID and location
    // List<Property> propertiesInLocation =
    // propertyRepository.findByUserIdAndLocation(userId, located);
    // return modelMapper.map(propertiesInLocation, new
    // TypeToken<List<PropertyDTO>>() {}.getType());
    // }

}
