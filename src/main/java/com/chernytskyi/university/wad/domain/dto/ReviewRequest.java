package com.chernytskyi.university.wad.domain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class ReviewRequest {

    @Max(value = 10, message = "rating must be from 1 to 10")
    @Min(value = 1, message = "rating must be from 1 to 10")
    private Integer rating;
    @NotBlank(message = "comment is mandatory")
    private String comment;
    @Positive(message = "user id must be positive")
    private Long userId;
    @Positive(message = "movie id must be positive")
    private Long movieId;

    public ReviewRequest() {}

    public ReviewRequest(Integer rating, String comment, Long userId, Long movieId) {
        this.rating = rating;
        this.comment = comment;
        this.userId = userId;
        this.movieId = movieId;
    }

    public Integer getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getMovieId() {
        return movieId;
    }
}
