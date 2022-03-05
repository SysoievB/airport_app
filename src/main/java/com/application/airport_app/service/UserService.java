package com.application.airport_app.service;

import com.application.airport_app.entities.User;

public interface UserService extends BaseService<User> {

    User register(User user);

    User findByUsername(String username);

    void activate(User user);
}
