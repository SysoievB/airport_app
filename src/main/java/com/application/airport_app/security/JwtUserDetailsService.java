package com.application.airport_app.security;

import com.application.airport_app.entities.AccountStatus;
import com.application.airport_app.entities.User;
import com.application.airport_app.security.jwt.JwtUser;
import com.application.airport_app.security.jwt.JwtUserFactory;
import com.application.airport_app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);

        if (user == null || user.getStatus() == AccountStatus.DELETED) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        if (user.getStatus() == AccountStatus.NOT_ACTIVE) {
            throw new UsernameNotFoundException("User with username: " + username + " not verified");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("IN loadUserByUsername - user with username: {} successfully loaded", username);

        return jwtUser;
    }
}
