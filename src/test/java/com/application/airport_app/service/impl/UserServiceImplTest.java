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
        Role userRole = new Role(1L, AccountStatus.ACTIVE, "ROLE_USER");
        List<Role> roleList = List.of(userRole);
        User user = new User(1L, roleList);
        Date timeUpdatedBefore = user.getUpdated();

        User updatedUser = new User("updated", "updated", AccountStatus.NOT_ACTIVE, timeUpdatedBefore, roleList);

        // When
        when(roleRepository.findByName("ROLE_USER")).thenReturn(userRole);
        when(underTestUserRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(updatedUser.getPassword());

        underTestUserService.update(user.getId(), updatedUser);

        // Then
        assertEquals(updatedUser.getUsername(), user.getUsername());
        assertEquals(updatedUser.getPassword(), user.getPassword());
        assertEquals(AccountStatus.NOT_ACTIVE, user.getStatus());
        assertNotEquals(timeUpdatedBefore, user.getUpdated());
        assertEquals(updatedUser.getRoles().size(), user.getRoles().size());

        verify(underTestUserRepository, atLeastOnce()).save(user);
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
        var users = List.of(
                new User(1L),
                new User(2L)
        );

        // When
        when(underTestUserRepository.findAll()).thenReturn(users);

        List<User> actualResult = underTestUserService.list();

        // Then
        assertEquals(users.size(), actualResult.size());

        verify(underTestUserRepository, atLeastOnce()).findAll();
    }

    @Test
    void itShouldRegisterUser() {
        // Given
        Role userRole = new Role(1L, AccountStatus.ACTIVE, "ROLE_USER");
        List<Role> roleList = List.of(userRole);
        User user = new User(roleList);

        // When
        when(roleRepository.findByName("ROLE_USER")).thenReturn(userRole);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(user.getPassword());
        when(underTestUserRepository.save(user)).thenReturn(user);

        underTestUserService.register(user);

        // Then
        assertEquals(user.getStatus(), AccountStatus.NOT_ACTIVE);
        assertNotNull(user.getCreated());
        assertNotNull(user.getUpdated());
        assertTrue(user.getRoles().contains(userRole));

        verify(underTestUserRepository, atLeastOnce()).save(user);
    }

    @Test
    void itShouldFindUserByUsername() {
        // Given
        String userName = "userName";
        User user = new User(1L, userName);

        // When
        when(underTestUserRepository.findByUsername(userName)).thenReturn(Optional.of(user));

        User actualUser = underTestUserService.findByUsername(userName);

        // Then
        Long expectedId = 1L;
        assertEquals(expectedId, actualUser.getId());

        verify(underTestUserRepository, atLeastOnce()).findByUsername(anyString());
    }

    @Test
    void itShouldActivateUser() {
        // Given
        User user = new User(1L);

        Date timeUpdatedBefore = user.getUpdated();
        Date timeCreatedBefore = user.getCreated();

        // When
        underTestUserService.activate(user);

        // Then
        assertEquals(user.getStatus(), AccountStatus.ACTIVE);
        assertNotEquals(timeUpdatedBefore, user.getUpdated());
        assertEquals(timeCreatedBefore, user.getCreated());

        verify(underTestUserRepository, atLeastOnce()).save(user);


    }
}