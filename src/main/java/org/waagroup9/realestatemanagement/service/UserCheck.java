package org.waagroup9.realestatemanagement.service;


import org.waagroup9.realestatemanagement.model.entity.User;

public interface UserCheck {
    User findUserByEmail(String email);
}