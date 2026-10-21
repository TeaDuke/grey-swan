package com.chernytskyi.university.wad.controller;

import com.chernytskyi.university.wad.domain.User;
import com.chernytskyi.university.wad.domain.dto.UserRequest;
import com.chernytskyi.university.wad.domain.dto.UserResponse;
import com.chernytskyi.university.wad.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/api")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<UserResponse> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse addUser(@Valid @RequestBody UserRequest userRequest) {
        return userService.add(userRequest);
    }

    @PutMapping("/users/{id}")
    public UserResponse updateUser(@Valid @RequestBody UserRequest userRequest, @PathVariable Long id) {
        return userService.update(userRequest, id);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
