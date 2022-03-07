package com.application.airport_app.dto;

import com.application.airport_app.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegisterDto {

    private Long id;
    private String username;
    private String password;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);

        return user;
    }

}
