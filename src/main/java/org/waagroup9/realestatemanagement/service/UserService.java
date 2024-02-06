package org.waagroup9.realestatemanagement.service;

import java.util.List;

import org.waagroup9.realestatemanagement.dto.UserDTO;

public interface UserService {

    List<UserDTO> getAllUsers();

    UserDTO getUserById(int userId);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(int userId, UserDTO userDTO);

    void deleteUser(int userId);
}
