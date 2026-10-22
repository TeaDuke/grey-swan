package com.chernytskyi.university.wad.domain.dto;

import jakarta.validation.constraints.NotBlank;

public class GenreRequest {
    @NotBlank(message = "name is mandatory")
    private String name;
    @NotBlank(message = "description is mandatory")
    private String description;

    public GenreRequest() {}

    public GenreRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
