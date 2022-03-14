package com.application.airport_app.service.impl;

import com.application.airport_app.entities.AccountStatus;
import com.application.airport_app.entities.Role;
import com.application.airport_app.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository underTestRoleRepository;

    @InjectMocks
    private RoleServiceImpl underTestRoleService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void itShouldListRoles() {
        // Given
        List<Role> roles = new ArrayList<>(Arrays.asList(
                new Role(1L, AccountStatus.ACTIVE, "ROLE_USER"),
                new Role(2L, AccountStatus.ACTIVE, "USER_ADMIN")
        ));

        given(underTestRoleRepository.findAll()).willReturn(roles);

        // When
        List<Role> actualResult = underTestRoleService.list();

        // Then
        assertEquals(roles.size(), actualResult.size());
        verify(underTestRoleRepository, atLeastOnce()).findAll();
    }
}
