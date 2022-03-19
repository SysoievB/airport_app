package com.application.airport_app.controller;

import com.application.airport_app.dto.UserDto;
import com.application.airport_app.entities.AccountStatus;
import com.application.airport_app.entities.Role;
import com.application.airport_app.entities.User;
import com.application.airport_app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserRestControllerV1Test {

    @Mock
    private UserService underTestUserService;

    @InjectMocks
    private UserRestControllerV1 underTestUserRestController;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void itShouldGetUser() {
        // Given
        Role userRole = new Role(1L, AccountStatus.ACTIVE, "ROLE_USER");
        List<Role> roleList = List.of(userRole);
        User user = new User(1L, roleList);

        // When
        when(underTestUserService.getById(user.getId())).thenReturn(user);
        ResponseEntity<UserDto> response = underTestUserRestController.get(user.getId());

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user.getUsername(), Objects.requireNonNull(response.getBody()).getUsername());
        assertEquals(user.getId(), response.getBody().getId());
    }

    @Test
    public void itShouldGetUserFail() {
        // Given
        Long id = 1L;

        // When
        when(underTestUserService.getById(id)).thenReturn(null);
        ResponseEntity<UserDto> response1 = underTestUserRestController.get(null);
        ResponseEntity<UserDto> response2 = underTestUserRestController.get(id);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
    }

    @Test
    public void itShouldGetAllUsers() {
        // Given
        Role userRole1 = new Role(1L, AccountStatus.ACTIVE, "ROLE_USER");
        List<Role> roleList1 = List.of(userRole1);
        Role userRole2 = new Role(1L, AccountStatus.ACTIVE, "ROLE_USER");
        List<Role> roleList2 = List.of(userRole2);

        User user1 = new User(1L, roleList1);
        User user2 = new User(2L, roleList2);

        var users = List.of(user1, user2);

        // When
        when(underTestUserService.list()).thenReturn(users);

        ResponseEntity<List<UserDto>> response = underTestUserRestController.getAll();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users.size(), Objects.requireNonNull(response.getBody()).size());
        assertEquals(user1.getId(), response.getBody().get(0).getId());
        assertEquals(user2.getId(), response.getBody().get(1).getId());
    }

    @Test
    public void itShouldGetAllUsersFail() {
        // When
        when(underTestUserService.list()).thenReturn(new ArrayList<>());

        ResponseEntity<List<UserDto>> response = underTestUserRestController.getAll();

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void itShouldSaveUser() {
        // Given
        User user = new User(1L, "userName");

        // When
        ResponseEntity<User> response = underTestUserRestController.save(user);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user.getUsername(), Objects.requireNonNull(response.getBody()).getUsername());

        verify(underTestUserService, atLeastOnce()).save(user);
    }

    @Test
    public void itShouldFailWhenSaveUser() {
        // When
        ResponseEntity<User> response = underTestUserRestController.save(null);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(underTestUserService, never()).save(any(User.class));
    }

    @Test
    public void itShouldUpdateUser() {
        // Given
        User user1 = new User(1L, "userName1");
        User user2 = new User(2L, "userName2");

        // When
        ResponseEntity<User> response = underTestUserRestController.update(user1.getId(), user2);

        // Then
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(user2.getUsername(), Objects.requireNonNull(response.getBody()).getUsername());

        verify(underTestUserService, atLeastOnce()).update(user1.getId(), user2);
    }

    @Test
    public void itShouldUpdateUserFail() {
        // When
        ResponseEntity<User> response = underTestUserRestController.update(null, null);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(underTestUserService, never()).update(anyLong(), any(User.class));
    }

    @Test
    public void itShouldDeleteUser() {
        // Given
        User user = new User(1L, "userName");

        // When
        when(underTestUserService.getById(user.getId())).thenReturn(user);

        // Then
        var response = underTestUserRestController.delete(user.getId());

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

        verify(underTestUserService, atLeastOnce()).delete(user.getId());
    }

    @Test
    public void itShouldDeleteUserFail() {
        // Given
        Long id = 1L;

        // When
        when(underTestUserService.getById(id)).thenReturn(null);

        var response = underTestUserRestController.delete(id);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(underTestUserService, never()).delete(anyLong());
    }
}