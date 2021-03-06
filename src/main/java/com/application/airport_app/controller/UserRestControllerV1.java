package com.application.airport_app.controller;

import com.application.airport_app.dto.UserDto;
import com.application.airport_app.entities.User;
import com.application.airport_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestControllerV1 {

    private final UserService userService;

    @Autowired
    public UserRestControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> get(@PathVariable Long id) {

        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        User user = this.userService.getById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(UserDto.fromUser(user));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        List<User> users = this.userService.list();

        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(UserDto.toUserDtos(users));
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {

        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        this.userService.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {

        if (id == null || user == null) {
            return ResponseEntity.badRequest().build();
        }

        this.userService.update(id, user);

        return ResponseEntity.accepted().body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id) {

        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        User user = this.userService.getById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        this.userService.delete(id);

        return ResponseEntity.accepted().build();
    }
}
