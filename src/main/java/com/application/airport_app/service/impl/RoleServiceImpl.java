package com.application.airport_app.service.impl;

import com.application.airport_app.entities.Role;
import com.application.airport_app.repository.RoleRepository;
import com.application.airport_app.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> list() {
        log.info("IN RoleServiceImpl list");

        return roleRepository.findAll();
    }
}
