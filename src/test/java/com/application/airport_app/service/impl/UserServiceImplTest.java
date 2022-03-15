package com.application.airport_app.service.impl;

import com.application.airport_app.entities.AccountStatus;
import com.application.airport_app.entities.Role;
import com.application.airport_app.entities.User;
import com.application.airport_app.repository.RoleRepository;
import com.application.airport_app.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {

    @Mock
    private UserRepository underTestUserRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl underTestUserService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void itShouldSaveUser() {
        // Given
        Role userRole = new Role(1L, AccountStatus.ACTIVE, "ROLE_USER");
        List<Role> roleList = List.of(userRole);
        User user = new User(roleList);

        // When
        when(roleRepository.findByName("ROLE_USER")).thenReturn(userRole);

        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(user.getPassword());

        underTestUserService.save(user);

        // Then
        assertEquals(user.getStatus(), AccountStatus.ACTIVE);
        assertNotNull(user.getCreated());
        assertNotNull(user.getUpdated());

        verify(underTestUserRepository, atLeastOnce()).save(user);

        user.getRoles().forEach(
                role -> verify(roleRepository, atLeastOnce()).findByName(role.getName())
        );
    }

    @Test
    void itShouldUpdateUser() {
        // Given
        // When
        // Then
    }

    @Test
    void itShouldDeleteUser() {
        // Given
        User user = new User(1L);

        Date timeUpdatedBefore = user.getUpdated();
        Date timeCreatedBefore = user.getCreated();

        // When
        when(underTestUserRepository.findById(1L)).thenReturn(Optional.of(user));

        underTestUserService.delete(user.getId());

        // Then
        assertEquals(user.getStatus(), AccountStatus.DELETED);
        Assertions.assertNotEquals(timeUpdatedBefore, user.getUpdated());
        assertEquals(timeCreatedBefore, user.getCreated());

        verify(underTestUserRepository, atLeastOnce()).save(user);
    }

    @Test
    void itShouldGetByIdUser() {
        // Given
        User user = new User(1L);

        // When
        when(underTestUserRepository.findById(user.getId())).thenReturn(Optional.of(user));

        underTestUserService.getById(user.getId());

        // Then
        assertEquals(1L, user.getId());

        verify(underTestUserRepository, atLeastOnce()).findById(1L);
    }

    @Test
    void itShouldListUsers() {
        // Given
        // When
        // Then
    }

    @Test
    void itShouldRegister() {
        // Given
        // When
        // Then
    }

    @Test
    void itShouldFindByUsername() {
        // Given
        // When
        // Then
    }

    @Test
    void itShouldActivate() {
        // Given
        // When
        // Then
    }
}