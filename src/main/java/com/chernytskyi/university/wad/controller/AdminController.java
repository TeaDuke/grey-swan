package com.chernytskyi.university.wad.controller;

import com.chernytskyi.university.wad.domain.dto.UserResponse;
import com.chernytskyi.university.wad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<UserResponse> getAllAdmins() {
        return userService.findAllAdmins();
    }

    @PostMapping("/add/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAdmin(@PathVariable Long id) {
        userService.addAdmin(id);
    }

    @DeleteMapping("/remove/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAdmin(@PathVariable Long id) {
        userService.removeAdmin(id);
    }
}
