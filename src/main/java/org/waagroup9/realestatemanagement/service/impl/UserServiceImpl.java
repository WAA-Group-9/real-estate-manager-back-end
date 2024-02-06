package org.waagroup9.realestatemanagement.service.impl;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.waagroup9.realestatemanagement.config.CustomError;
import org.waagroup9.realestatemanagement.dto.UserDTO;
import org.waagroup9.realestatemanagement.model.AuditData;
import org.waagroup9.realestatemanagement.model.entity.PasswordResetToken;
import org.waagroup9.realestatemanagement.model.UserType;
import org.waagroup9.realestatemanagement.model.entity.User;
import org.waagroup9.realestatemanagement.model.entity.VerificationToken;
import org.waagroup9.realestatemanagement.repository.PasswordResetTokenRepository;
import org.waagroup9.realestatemanagement.repository.UserRepository;
import org.waagroup9.realestatemanagement.repository.VerificationTokenRepository;
import org.waagroup9.realestatemanagement.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;


    @Override
    public User registerAdmin(UserDTO userDTO) throws CustomError {
        User user = new User();

        //Add Audit Data
        AuditData auditData = new AuditData();
        auditData.setCreatedBy(userDTO.getUserName());
        auditData.setUpdatedBy(userDTO.getUserName());
        auditData.setCreatedOn(LocalDateTime.now());
        auditData.setUpdatedOn(LocalDateTime.now());

        user.setAuditData(auditData);
        user.setUserName(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());
        user.setUserType(UserType.ADMIN);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);

        return user;
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            return "invalidToken";
        }
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }
        //Activate the user
        user.setActive(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String existingToken) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(existingToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken
                = passwordResetTokenRepository.findByToken(token);

        if (passwordResetToken == null) {
            return "invalid";
        }

        User user = passwordResetToken.getUser();
        Calendar cal = Calendar.getInstance();

        if ((passwordResetToken.getExpiryDate().getTime()
                - cal.getTime().getTime()) <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return "expired";
        }

        return "valid";
    }

    @Override
    public Optional<User> getUserByPasswordToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) throws CustomError {
        return userRepository.findById(id).orElseThrow(() -> new CustomError("User not found"));
    }

    @Override
    public void deleteUser(Long id) throws CustomError {
        if (userRepository.findById(id).isEmpty()) {
            throw new CustomError("User with ID : " + id + " does not exist");
        } else {
            passwordResetTokenRepository.deleteById((long) id);
            verificationTokenRepository.deleteById((long) id);
            userRepository.deleteById(id);
        }
    }

    @Override
    public User updateUserDetails(Long id, UserDTO updatedUser) throws CustomError {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new CustomError("User with ID : " + id + " does not exist"));

        if (updatedUser.getUserName() != null) {
            existingUser.setUserName(updatedUser.getUserName());
        }
        if (updatedUser.getUserType() != null) {
            existingUser.setUserType(updatedUser.getUserType());
        }
        return userRepository.save(existingUser);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    private Boolean checkPassword(User user, String rawPassword) {
        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            return true;
        } else {
            throw new BadCredentialsException("Bad Credentials");
        }
    }

}