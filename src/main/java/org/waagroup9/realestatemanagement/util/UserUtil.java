package org.waagroup9.realestatemanagement.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.waagroup9.realestatemanagement.config.CustomError;
import org.waagroup9.realestatemanagement.model.UserType;
import org.waagroup9.realestatemanagement.service.UserService;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserUtil {

    private final UserService userService;

    public String getEmailFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
            Map<String, Object> attributes = jwtAuthenticationToken.getTokenAttributes();
            return (String) attributes.get("email");
        }
        return null;
    }

    public UserType getCurrentUserType(){
        return userService.getUserByEmail(getEmailFromAuthentication()).getUserType();
    }
}
