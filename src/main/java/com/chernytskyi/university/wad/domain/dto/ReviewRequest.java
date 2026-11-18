package com.chernytskyi.university.wad.domain.dto;

import jakarta.validation.constraints.*;

public class ReviewRequest {

    @Max(value = 10, message = "rating must be from 1 to 10")
    @Min(value = 1, message = "rating must be from 1 to 10")
    @NotNull(message = "rating is mandatory")
    private Integer rating;
    @NotBlank(message = "comment is mandatory")
    private String comment;
    @Positive(message = "movie id must be positive")
    @NotNull(message = "user id is mandatory")
    private Long movieId;

    public ReviewRequest() {}

    public ReviewRequest(Integer rating, String comment, Long movieId) {
        this.rating = rating;
        this.comment = comment;
        this.movieId = movieId;
    }

    public Integer getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Long getMovieId() {
        return movieId;
    }
}
