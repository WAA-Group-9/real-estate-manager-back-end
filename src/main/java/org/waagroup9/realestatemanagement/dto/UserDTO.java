package org.waagroup9.realestatemanagement.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int id;
    private String name;
    private String email;
    private String username;
    private String password;
    private String telephone;
    private UserTypeDTO userType;
    private AddressDTO address;
}
