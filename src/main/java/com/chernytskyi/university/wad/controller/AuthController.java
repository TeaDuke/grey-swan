package com.chernytskyi.university.wad.controller;

import com.chernytskyi.university.wad.domain.dto.LoginRequest;
import com.chernytskyi.university.wad.domain.dto.RefreshRequest;
import com.chernytskyi.university.wad.domain.dto.TokenResponse;
import com.chernytskyi.university.wad.domain.dto.UserRequest;
import com.chernytskyi.university.wad.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(path = "/register", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody UserRequest req) {
        authService.register(req);
    }

    @PostMapping(path = "/login", produces = "application/json")
    public TokenResponse login(@Valid @RequestBody LoginRequest req) {
        return authService.login(req);
    }

    @PostMapping(path = "/refresh", produces = "application/json")
    public TokenResponse refresh(@Valid @RequestBody RefreshRequest req) {
        return authService.refresh(req.refreshToken());
    }

    @PostMapping(path = "/logout", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@Valid @RequestBody RefreshRequest req) {
        authService.logout(req.refreshToken());
    }

    @PostMapping(path = "/admin/{id}")
    public void makeAdmin(@PathVariable Long id) {

    }
}
