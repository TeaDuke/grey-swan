package com.chernytskyi.university.wad.domain.dto;

import com.chernytskyi.university.wad.domain.Review;

public record ReviewResponse(Long id, Integer rating, String comment, Long userId, Long movieId) {
    public static ReviewResponse from(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getRating(),
                review.getComment(),
                review.getUser().getId(),
                review.getMovie().getId()
        );
    }
}
