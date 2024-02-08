package org.waagroup9.realestatemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTokenResponseDTO {
    private UserDTO user;
    private TokenResponse authorization;
}