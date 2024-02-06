package org.waagroup9.realestatemanagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.waagroup9.realestatemanagement.model.entity.User;
import org.waagroup9.realestatemanagement.repository.UserRepository;
import org.waagroup9.realestatemanagement.service.UserCheck;

@Service
public class UserCheckImpl implements UserCheck {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}