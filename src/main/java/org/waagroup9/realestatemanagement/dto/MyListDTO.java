package org.waagroup9.realestatemanagement.dto;

import lombok.Data;
import org.waagroup9.realestatemanagement.model.entity.User;

import java.util.List;

@Data
public class MyListDTO {
    private User user;
    private List<PropertyDTO> properties;
}