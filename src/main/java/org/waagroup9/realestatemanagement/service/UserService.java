package org.waagroup9.realestatemanagement.service;

import org.springframework.stereotype.Service;
import org.waagroup9.realestatemanagement.config.CustomError;
import org.waagroup9.realestatemanagement.dto.MyListDTO;
import org.waagroup9.realestatemanagement.dto.OfferDTO;
import org.waagroup9.realestatemanagement.dto.UserDTO;
import org.waagroup9.realestatemanagement.model.entity.User;
import org.waagroup9.realestatemanagement.model.entity.VerificationToken;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    User registerUser(UserDTO userDTO) throws CustomError;

    void createVerificationToken(User user, String token);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String existingToken);

    User findUserByEmail(String email);


    void createPasswordResetTokenForUser(User user, String token);

    String validatePasswordResetToken(String token);

    Optional<User> getUserByPasswordToken(String token);

    void changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String oldPassword);

    List<User> getAllUsers();

    User getUserById(Long id) throws CustomError;

    public void deleteUser(Long id) throws CustomError;


    User updateUserDetails(Long id, UserDTO user) throws CustomError;

    User getUserByEmail(String email);


    List<OfferDTO> getUserOffers(Long id);

    List<MyListDTO> getUserList(Long id);

    void addPropertyToMyList(Long id, Long propertyId);
}