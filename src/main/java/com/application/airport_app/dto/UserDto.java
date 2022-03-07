package com.application.airport_app.dto;

import com.application.airport_app.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private Long id;
    private String username;
    private List<RolesDto> roles;

    public static UserDto fromUser(User user) {

        var userRoles = user.getRoles().stream()
                .map(RolesDto::fromRole)
                .collect(Collectors.toCollection(ArrayList::new));

        return new UserDto(
                user.getId(),
                user.getUsername(),
                userRoles
                );
    }

    public static List<UserDto> toUserDtos(List<User> users) {

       return users.stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
