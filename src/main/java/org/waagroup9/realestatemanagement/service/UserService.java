package org.waagroup9.realestatemanagement.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.waagroup9.realestatemanagement.config.CustomError;
import org.waagroup9.realestatemanagement.dto.UserDTO;
import org.waagroup9.realestatemanagement.model.entity.User;
import org.waagroup9.realestatemanagement.model.entity.VerificationToken;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    User registerAdmin(UserDTO userDTO) throws CustomError;

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

    User getUserById(int id) throws CustomError;

    public void deleteUser(int id) throws CustomError;

    User updateUserDetails(int id, UserDTO user) throws CustomError;


}