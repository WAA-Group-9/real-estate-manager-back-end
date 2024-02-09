package org.waagroup9.realestatemanagement.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.waagroup9.realestatemanagement.config.CustomError;
import org.waagroup9.realestatemanagement.config.advice.annotations.CheckUserAccess;
import org.waagroup9.realestatemanagement.config.event.RegistrationCompleteEvent;
import org.waagroup9.realestatemanagement.dto.*;
import org.waagroup9.realestatemanagement.model.entity.User;
import org.waagroup9.realestatemanagement.model.entity.VerificationToken;
import org.waagroup9.realestatemanagement.service.UserService;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Value("${spring.security.oauth2.client.registration.google.clientId}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.clientSecret}")
    private String clientSecret;




    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO, HttpServletRequest request) {

        try {
            User user = userService.registerUser(userDTO);
            eventPublisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        } catch (CustomError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Successfully Registered, check your email to verify your account", HttpStatus.OK);
    }

    @GetMapping
    @CheckUserAccess
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @CheckUserAccess
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws CustomError {

        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @CheckUserAccess
    public ResponseEntity<?> deleteUser(@PathVariable Long id) throws CustomError {
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @CheckUserAccess
    public ResponseEntity<UserDTO> updateUserDetails(@PathVariable Long id, @RequestBody UserDTO user) {
        try {
            userService.updateUserDetails(id, user);
            return ResponseEntity.ok(user);
        } catch (CustomError e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/resendRegistrationToken")
    public ResponseEntity<?> resendRegistrationToken(@RequestParam("token") String existingToken, HttpServletRequest request) {
        VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        User user = newToken.getUser();
        resendVerificationToken(user, newToken, applicationUrl(request));
        return new ResponseEntity<>("Token Re-sent Successfully", HttpStatus.OK);
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordDTO passwordDTO, HttpServletRequest request) {
        User user = userService.findUserByEmail(passwordDTO.getEmail());
        String url = applicationUrl(request);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            url = passwordResetTokenMail(user, token, applicationUrl(request));
        }
        return "Password Reset link: " + url;
    }

    private String passwordResetTokenMail(User user, String token, String appUrl) {
        String url = appUrl + "/savePassword?id=" + user.getId() + "&token=" + token;
        //send password reset Email
        log.info("Click the link to reset your password: " + url);
        return url;
    }

    @PostMapping("/savePassword")
    public ResponseEntity<?> savePassword(@RequestParam("token") String token, @RequestBody PasswordDTO passwordDTO) {
        String result = userService.validatePasswordResetToken(token);

        if (!result.equals("valid")) {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }
        Optional<User> user = userService.getUserByPasswordToken(token);
        if (user.isPresent()) {
            userService.changeUserPassword(user.get(), passwordDTO.getNewPassword());
            return new ResponseEntity<>("Password Changed Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid Token", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/changePassword")
    public String ChangePassword(@RequestBody PasswordDTO passwordDTO) {
        User user = userService.findUserByEmail(passwordDTO.getEmail());
        if (!userService.checkIfValidOldPassword(user, passwordDTO.getOldPassword())) {
            return "Invalid Old Password";
        }
        //Save new password
        userService.changeUserPassword(user, passwordDTO.getNewPassword());
        return "password changed successfully";
    }

    private void resendVerificationToken(User user, VerificationToken newToken, String appUrl) {
        String url = appUrl + "/registrationConfirm?token=" + newToken.getToken();
        //send verification Email
        log.info("Click the link to verify your account: " + url);
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = userService.validateVerificationToken(token);
        if (result.equalsIgnoreCase("valid")) {
            return "User Verified Successfully";
        }
        return "Bad User";
    }

    //TODO needs a lot of refactoring
    @PostMapping("/auth/token")
    public ResponseEntity<UserTokenResponseDTO> exchangeAuthorizationCodeForToken(@RequestBody AuthorizationCode authorizationCode) {
        RestTemplate restTemplate = new RestTemplate();

        String redirectUri = "http://localhost:3000";
        String grantType = "authorization_code";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("code", authorizationCode.getAuthorizationCode());
        map.add("redirect_uri", redirectUri);
        map.add("grant_type", grantType);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<TokenResponse> response = restTemplate.exchange("https://oauth2.googleapis.com/token", HttpMethod.POST, request, TokenResponse.class);

        // Extract the ID token and decode it to get the user's email
        String idToken = response.getBody().getId_token();
        String email = decodeEmailFromIdToken(idToken);

        // Get the user data from the database using the email
        User user = userService.findUserByEmail(email);
        // Create a UserDTO object from the user data
        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getUserName(), user.getEmail(), null, user.getUserType());

        // Create a UserTokenResponseDTO object that contains the UserDTO and TokenResponse
        UserTokenResponseDTO userTokenResponseDTO = new UserTokenResponseDTO(userDTO, response.getBody());

        return ResponseEntity.ok(userTokenResponseDTO);
    }


    private String decodeEmailFromIdToken(String idToken) {
        DecodedJWT jwt = JWT.decode(idToken);
        String email = jwt.getClaim("email").asString();

        return email;
    }


    private String applicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() + "/api/v1/user" +
                request.getContextPath();
    }

    @ExceptionHandler({CustomError.class, UserPrincipalNotFoundException.class})
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }


    //TODO : add to mylist endpoint
    @PostMapping("{id}/mylist/{propertyId}")
    public ResponseEntity<?> addToMyList(@PathVariable Long id, @PathVariable Long propertyId) {
        userService.addPropertyToMyList(id, propertyId);
        return new ResponseEntity<>("Added to MyList", HttpStatus.OK);
    }

    @GetMapping("{id}/mylist")
    @CheckUserAccess
    public ResponseEntity<List<PropertyDTO>> getUserMyList(@PathVariable Long id) {
        List<PropertyDTO> myFavoriteProperties = userService.getMyFavoriteProperties(id);
        return ResponseEntity.ok(myFavoriteProperties);
    }


    @GetMapping("{id}/offers")
    @CheckUserAccess
    public ResponseEntity<List<OfferDTO>> getUserOffers(@PathVariable Long id) {
        List<OfferDTO> offers = userService.getUserOffers(id);
        return ResponseEntity.ok(offers);
    }

    @GetMapping("auth/")
    public ResponseEntity<?> getAuthenticatedUser() {
        UserDTO user = userService.getUserByToken();
        return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
    }

}