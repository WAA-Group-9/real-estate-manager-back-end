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
import org.waagroup9.realestatemanagement.model.entity.User;
import org.waagroup9.realestatemanagement.repository.UserRepository;

import java.util.Map;
import java.util.Objects;

@Aspect
@Component
public class CheckUserAccessAspect {

    @Autowired
    private UserRepository userRepository;

    @Before("@annotation(org.waagroup9.realestatemanagement.config.advice.annotations.CheckUserAccess)")
    public void checkUserAccess(JoinPoint joinPoint) throws CustomError {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof Long) {
            Long id = (Long) args[0];
            String email = getEmailFromAuthentication();
            User user = userRepository.findByEmail(email);

            if (user != null) {
                if (!Objects.equals(user.getId(), id)) {
                    throw new CustomError("Access denied");
                }
            } else {
                throw new CustomError("User not found");
            }

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