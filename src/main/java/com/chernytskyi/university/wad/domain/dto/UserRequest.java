package com.chernytskyi.university.wad.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class UserRequest {
    private Long id;
    @NotBlank(message = "username is mandatory")
    private String username;
    @NotBlank(message = "password is mandatory")
    private String password;
    @NotBlank(message = "email is mandatory")
    private String email;
    @NotEmpty(message = "image is mandatory")
    private byte[] image;

    public UserRequest() {}

    public UserRequest(String username, String password, String email, byte[] image) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public byte[] getImage() {
        return image;
    }
}
