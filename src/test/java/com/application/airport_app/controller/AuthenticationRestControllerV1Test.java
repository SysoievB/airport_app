package com.application.airport_app.controller;

import com.application.airport_app.dto.AuthenticationRequestDto;
import com.application.airport_app.dto.UserRegisterDto;
import com.application.airport_app.entities.User;
import com.application.airport_app.security.jwt.JwtTokenProvider;
import com.application.airport_app.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationRestControllerV1Test {

    @Mock
    private AuthenticationManager underTestAuthenticationManager;
    @Mock
    private JwtTokenProvider underTestJwtTokenProvider;
    @Mock
    private UserService underTestUserService;

    @InjectMocks
    private AuthenticationRestControllerV1 underTestAuthenticationRestController;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void itShouldLogin() {
        // Given
        var user = new User(1L, "username", "test");

        AuthenticationRequestDto userDto = new AuthenticationRequestDto();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());

        // When
        String tokenNumber = "tokenNumber";
        when(underTestUserService.findByUsername(userDto.getUsername())).thenReturn(user);
        when(underTestJwtTokenProvider.createToken(user.getUsername(), user.getRoles())).thenReturn(tokenNumber);

        ResponseEntity<Map<Object, Object>> response = underTestAuthenticationRestController.login(userDto);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user.getUsername(), Objects.requireNonNull(response.getBody()).get("username"));
        assertEquals(tokenNumber, response.getBody().get("token"));

        verify(underTestAuthenticationManager, atLeastOnce()).authenticate(any());
    }

    @Test
    public void  itShouldFailLogin() {
        // Given
        var user = new User(1L, "username", "test");

        AuthenticationRequestDto userDto = new AuthenticationRequestDto();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());

        // When
        when(underTestUserService.findByUsername(userDto.getUsername())).thenReturn(null);

        ResponseEntity<Map<Object, Object>> response = underTestAuthenticationRestController.login(userDto);

        // Then
        String message = (String) Objects.requireNonNull(response.getBody()).get("message");
        assertTrue(message.contains("User with username"));
    }

    @Test
    public void  itShouldRegisterUserAccount() {
        // Given
        var user = new User(1L, "test", "test");

        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setId(user.getId());
        userRegisterDto.setPassword(user.getPassword());
        userRegisterDto.setUsername(user.getUsername());

        // When
        when(underTestUserService.findByUsername(userRegisterDto.getUsername())).thenReturn(null);

        ResponseEntity<Map<Object, Object>> response = underTestAuthenticationRestController.registerUserAccount(userRegisterDto);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully", Objects.requireNonNull(response.getBody()).get("message"));
    }

    @Test(expected = BadCredentialsException.class)
    public void  itShouldFailRegisterUserAccountByExistingUsername() {
        // Given
        var user = new User(1L, "test", "test");

        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setId(user.getId());
        userRegisterDto.setPassword(user.getPassword());
        userRegisterDto.setUsername(user.getUsername());

        // When
        when(underTestUserService.findByUsername(userRegisterDto.getUsername())).thenReturn(user);

        // Then
        underTestAuthenticationRestController.registerUserAccount(userRegisterDto);
    }
}