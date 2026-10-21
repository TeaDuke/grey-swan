package com.chernytskyi.university.wad.domain.dto;

import jakarta.validation.constraints.NotBlank;

public class GenreRequest {
    @NotBlank(message = "name is mandatory")
    private String name;
    @NotBlank(message = "name is mandatory")
    private String description;
    private Long parentGenreId;

    public GenreRequest() {}

    public GenreRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public GenreRequest(String name, String description, Long parentGenreId) {
        this.name = name;
        this.description = description;
        this.parentGenreId = parentGenreId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getParentGenreId() {
        return parentGenreId;
    }
}
