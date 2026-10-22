package com.chernytskyi.university.wad.service;

import com.chernytskyi.university.wad.domain.Movie;
import com.chernytskyi.university.wad.domain.Review;
import com.chernytskyi.university.wad.domain.User;
import com.chernytskyi.university.wad.domain.dto.ReviewRequest;
import com.chernytskyi.university.wad.domain.dto.ReviewResponse;
import com.chernytskyi.university.wad.exception.NoResourceException;
import com.chernytskyi.university.wad.repository.MovieRepository;
import com.chernytskyi.university.wad.repository.ReviewRepository;
import com.chernytskyi.university.wad.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReviewService implements RESTService<Review, ReviewRequest, ReviewResponse> {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public List<ReviewResponse> findAll() {
        return fromList(reviewRepository.findAll());
    }

    @Override
    @Transactional
    public ReviewResponse findById(Long id) throws NoSuchElementException {
        return ReviewResponse.from(reviewRepository.findById(id).orElseThrow(() -> new NoResourceException("Review not found")));
    }

    @Override
    @Transactional
    public ReviewResponse add(ReviewRequest reviewRequest) {
        Review review = new Review(reviewRequest.getRating(), reviewRequest.getComment(),
                                   getUser(reviewRequest.getUserId()), getMovie(reviewRequest.getMovieId()));
        updateMovieRating(review.getMovie().getId(), review.getRating(), 1);
        return ReviewResponse.from(reviewRepository.save(review));
    }

    @Override
    @Transactional
    public ReviewResponse update(ReviewRequest reviewRequest, Long id) throws NoSuchElementException {
        validateReviewRequest(reviewRequest);
        Review review = reviewRepository.findById(id).orElseThrow(() -> new NoResourceException("Review not found"));
        updateMovieRating(review.getMovie().getId(), reviewRequest.getRating() - review.getRating(), 0);
        review.setRating(reviewRequest.getRating());
        review.setComment(reviewRequest.getComment());
        return ReviewResponse.from(reviewRepository.save(review));
    }

    @Override
    @Transactional
    public void delete(Long id) throws NoSuchElementException {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new NoResourceException("Review not found"));
        updateMovieRating(review.getMovie().getId(), -review.getRating(), -1);
        reviewRepository.delete(review);
    }

    @Transactional
    public List<ReviewResponse> findAllByMovieId(Long id) {
        Movie movie = getMovie(id);
        return fromList(reviewRepository.findAllByMovie(movie));
    }

    @Transactional
    public List<ReviewResponse> findAllByUserId(Long id) {
        User user = getUser(id);
        return fromList(reviewRepository.findAllByUser(user));
    }

    private Movie getMovie(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new NoResourceException("Movie not found"));
    }

    private User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoResourceException("User not found"));
    }

    private List<ReviewResponse> fromList(List<Review> reviews) {
        return reviews.stream().map(ReviewResponse::from).toList();
    }

    private void updateMovieRating(Long movieId, Integer rating, Integer count) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new NoResourceException("Movie not found"));
        movie.setRatingCount(movie.getRatingCount() + count);
        movie.setRatingSum(movie.getRatingSum() + rating);
    }

    private void validateReviewRequest(ReviewRequest review) {
        userRepository.findById(review.getUserId()).orElseThrow(() -> new NoResourceException("User not found"));
        movieRepository.findById(review.getMovieId()).orElseThrow(() -> new NoResourceException("Movie not found"));
    }
}
