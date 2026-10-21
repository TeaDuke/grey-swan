package com.chernytskyi.university.wad.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.Set;

public class MovieRequest {
    @NotBlank(message = "title is mandatory")
    private String title;
    @NotEmpty(message = "cover is mandatory")
    private byte[] cover;
    @NotBlank(message = "director is mandatory")
    private String director;
    @Positive(message = "duration must be positive")
    private Integer duration; // minutes
    @Positive(message = "release year must be positive")
    private Integer releaseYear;
    @NotEmpty
    private Set<Long> genreIds;

    public MovieRequest() {}
    public MovieRequest(String title, byte[] cover, String director, Integer duration, Integer releaseYear, Set<Long> genreIds) {
        this.title = title;
        this.cover = cover;
        this.director = director;
        this.duration = duration;
        this.releaseYear = releaseYear;
        this.genreIds = genreIds;
    }

    public String getTitle() {
        return title;
    }

    public byte[] getCover() {
        return cover;
    }

    public String getDirector() {
        return director;
    }

    public Integer getDuration() {
        return duration;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public Set<Long> getGenreIds() {
        return genreIds;
    }
}
