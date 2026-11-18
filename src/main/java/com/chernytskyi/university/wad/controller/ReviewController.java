package com.chernytskyi.university.wad.controller;

import com.chernytskyi.university.wad.domain.Review;
import com.chernytskyi.university.wad.domain.dto.ReviewRequest;
import com.chernytskyi.university.wad.domain.dto.ReviewResponse;
import com.chernytskyi.university.wad.security.AdminUtil;
import com.chernytskyi.university.wad.service.ReviewService;
import jakarta.validation.Valid;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping(value = "/reviews", produces = "application/json")
    public List<ReviewResponse> getReviews() {
        return reviewService.findAll();
    }

    @GetMapping(value = "/reviews/{id}", produces = "application/json")
    public ReviewResponse getReview(@PathVariable Long id) {
        return reviewService.findById(id);
    }

    @PostMapping(value = "/reviews",  produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponse addReview(@Valid @RequestBody ReviewRequest reviewRequest, @AuthenticationPrincipal Jwt jwt) {
        return reviewService.add(reviewRequest, jwt.getSubject());
    }

    @PutMapping(value = "/reviews/{id}",  produces = "application/json")
    public ReviewResponse updateReview(@Valid @RequestBody ReviewRequest reviewRequest, @PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        return reviewService.update(reviewRequest, id, jwt.getSubject());
    }

    @DeleteMapping(value = "/reviews/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        reviewService.delete(id, jwt.getSubject(), AdminUtil.isAdmin(jwt));
    }

    @GetMapping(value = "/movies/{id}/reviews", produces = "application/json")
    public List<ReviewResponse> getReviewsOfMovie(@PathVariable Long id) {
        return reviewService.findAllByMovieId(id);
    }

    @GetMapping(value = "/users/{id}/reviews", produces = "application/json")
    public List<ReviewResponse> getReviewsOfUser(@PathVariable Long id) {
        return reviewService.findAllByUserId(id);
    }
}
