package org.waagroup9.realestatemanagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.waagroup9.realestatemanagement.model.CustomUserDetails;
import org.waagroup9.realestatemanagement.model.entity.User;
import org.waagroup9.realestatemanagement.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        System.out.println("User: " + user);
        if (user == null) {
            throw new UsernameNotFoundException("No User Found");
        }
        return new CustomUserDetails(user);
    }
}