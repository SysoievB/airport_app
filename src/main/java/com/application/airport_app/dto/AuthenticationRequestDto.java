package com.application.airport_app.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDto {

    private String username;
    private String password;
}
