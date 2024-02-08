package org.waagroup9.realestatemanagement.dto;

import lombok.Getter;
import lombok.Setter;
import org.waagroup9.realestatemanagement.model.entity.User;

import java.util.List;

@Getter
@Setter
public class MyListDTO {
    private User user;
    private List<PropertyDTO> properties;
}