package com.application.airport_app.service.impl;

import com.application.airport_app.entities.AccountStatus;
import com.application.airport_app.entities.Role;
import com.application.airport_app.entities.User;
import com.application.airport_app.repository.RoleRepository;
import com.application.airport_app.repository.UserRepository;
import com.application.airport_app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.application.airport_app.util.DateUtil.getCurrentDate;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void save(User user) {
        if (user.getStatus() == null) user.setStatus(AccountStatus.ACTIVE);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCreated(getCurrentDate());
        user.setUpdated(getCurrentDate());

        List<Role> userRoles = user.getRoles().stream()
                .map(role -> roleRepository.findRoleByName(role.getName()))
                .collect(Collectors.toCollection(ArrayList::new));

        user.setRoles(userRoles);
        userRepository.save(user);
        log.info("IN UserServiceImpl save {}", user);
    }

    @Override
    public void update(Long id, User updatedUser) {
        User user = getById(id);

        if (updatedUser.getUsername() != null) user.setUsername(updatedUser.getUsername());

        if (updatedUser.getStatus() != null) user.setStatus(updatedUser.getStatus());

        if (updatedUser.getPassword() != null)
            user.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));

        if (updatedUser.getRoles() != null) {
            List<Role> userRoles = updatedUser.getRoles().stream()
                    .map(role -> roleRepository.findRoleByName(role.getName()))
                    .collect(Collectors.toList());

            user.setRoles(userRoles);
        }

        user.setUpdated(getCurrentDate());

        userRepository.save(user);
        log.info("IN UserServiceImpl update {}", user);
    }

    @Override
    public void delete(Long id) {
        User user = getById(id);
        user.setStatus(AccountStatus.DELETED);
        user.setUpdated(getCurrentDate());

        userRepository.save(user);
        log.info("IN UserServiceImpl delete {}", user);
    }

    @Override
    public User getById(Long id) {
        User result = userRepository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("User not found by this id :: " + id)
                );

        log.info("IN getById - user: {} found by id: {}", result, id);

        return result;
    }

    @Override
    public List<User> list() {
        log.info("IN UserServiceImpl list");

        return userRepository.findAll();
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findRoleByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(AccountStatus.NOT_ACTIVE);

        user.setCreated(getCurrentDate());
        user.setUpdated(getCurrentDate());

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository
                .findByUsername(username)
                .orElseThrow(
                        () -> new NoSuchElementException("User not found by this username :: " + username)
                );

        log.info("IN findByUsername - user found by username: {}", username);

        return result;
    }

    @Override
    public void activate(User user) {
        user.setStatus(AccountStatus.ACTIVE);
        user.setUpdated(getCurrentDate());
        userRepository.save(user);
    }
}
