package com.application.airport_app.dto;

import com.application.airport_app.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RolesDto {

    private Long id;
    private String name;

    public static RolesDto fromRole(Role role) {

        return new RolesDto(role.getId(), role.getName());
    }
}
