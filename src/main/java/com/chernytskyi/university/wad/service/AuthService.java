package com.chernytskyi.university.wad.service;

import com.chernytskyi.university.wad.domain.User;
import com.chernytskyi.university.wad.domain.dto.LoginRequest;
import com.chernytskyi.university.wad.domain.dto.TokenResponse;
import com.chernytskyi.university.wad.domain.dto.UserRequest;
import com.chernytskyi.university.wad.exception.UsernameAlreadyUsedException;
import com.chernytskyi.university.wad.repository.UserRepository;
import com.chernytskyi.university.wad.security.AppUserDetails;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Transactional
    public void register(UserRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) throw new UsernameAlreadyUsedException();
        User user = new User(req.getUsername(), passwordEncoder.encode(req.getPassword()),
                req.getEmail(), req.getImage());
        userRepository.save(user);
    }

    public TokenResponse login(LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(req.username(), req.password()));
        User user = ((AppUserDetails) auth.getPrincipal()).getUser();
        return tokenService.issuePair(user);
    }

    public TokenResponse refresh(String refreshToken) {
        return tokenService.refresh(refreshToken);
    }

    public void logout(String refreshToken) {
        tokenService.revoke(refreshToken);
    }

}
