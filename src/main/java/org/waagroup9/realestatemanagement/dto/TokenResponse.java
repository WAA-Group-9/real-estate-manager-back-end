package org.waagroup9.realestatemanagement.dto;

import lombok.Data;

@Data
public class TokenResponse {
    private String access_token;
    private String refresh_token;
    private String id_token;
}

