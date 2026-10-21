package com.chernytskyi.university.wad.domain.dto;

import com.chernytskyi.university.wad.domain.User;

public record UserResponse(Long id, String username, String email, byte[] image) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getImage()
        );
    }
}
