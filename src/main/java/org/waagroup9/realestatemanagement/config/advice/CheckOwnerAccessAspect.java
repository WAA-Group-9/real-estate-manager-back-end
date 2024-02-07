package org.waagroup9.realestatemanagement.config.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.waagroup9.realestatemanagement.config.CustomError;
import org.waagroup9.realestatemanagement.model.UserType;
import org.waagroup9.realestatemanagement.model.entity.User;
import org.waagroup9.realestatemanagement.repository.UserRepository;

import java.util.Map;

@Aspect
@Component
public class CheckOwnerAccessAspect {

    @Autowired
    private UserRepository userRepository;

    @Before("@annotation(org.waagroup9.realestatemanagement.config.advice.annotations.CheckOwnerAccess)")
    public void checkOwnerAccess(JoinPoint joinPoint) throws CustomError {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) getEmailFromAuthentication();
        User user = userRepository.findByEmail(email);
        if (!user.getUserType().equals(UserType.OWNER)) {
            throw new CustomError("Access denied");
        }
    }

    private String getEmailFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
            Map<String, Object> attributes = jwtAuthenticationToken.getTokenAttributes();
            return (String) attributes.get("email");
        }
        return null;
    }
}