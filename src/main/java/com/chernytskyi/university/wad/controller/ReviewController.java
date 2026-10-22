package com.chernytskyi.university.wad.controller;

import com.chernytskyi.university.wad.domain.Review;
import com.chernytskyi.university.wad.domain.dto.ReviewRequest;
import com.chernytskyi.university.wad.domain.dto.ReviewResponse;
import com.chernytskyi.university.wad.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/reviews")
    public List<ReviewResponse> getReviews() {
        return reviewService.findAll();
    }

    @GetMapping("/reviews/{id}")
    public ReviewResponse getReview(@PathVariable Long id) {
        return reviewService.findById(id);
    }

    @PostMapping("/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponse addReview(@Valid @RequestBody ReviewRequest reviewRequest) {
        return reviewService.add(reviewRequest);
    }

    @PutMapping("/reviews/{id}")
    public ReviewResponse updateReview(@Valid @RequestBody ReviewRequest reviewRequest, @PathVariable Long id) {
        return reviewService.update(reviewRequest, id);
    }

    @DeleteMapping("/reviews/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable Long id) {
        reviewService.delete(id);
    }

    @GetMapping("/movies/{id}/reviews")
    public List<ReviewResponse> getReviewsOfMovie(@PathVariable Long id) {
        return reviewService.findAllByMovieId(id);
    }

    @GetMapping("/users/{id}/reviews")
    public List<ReviewResponse> getReviewsOfUser(@PathVariable Long id) {
        return reviewService.findAllByUserId(id);
    }
}
