package com.chernytskyi.university.wad.service;

import com.chernytskyi.university.wad.domain.*;
import com.chernytskyi.university.wad.domain.dto.UserRequest;
import com.chernytskyi.university.wad.domain.dto.UserResponse;
import com.chernytskyi.university.wad.exception.NoAccessException;
import com.chernytskyi.university.wad.exception.NoResourceException;
import com.chernytskyi.university.wad.repository.MovieRepository;
import com.chernytskyi.university.wad.repository.RefreshTokenRepository;
import com.chernytskyi.university.wad.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;


    
    @Transactional
    public List<UserResponse> findAll() {
        return fromList(userRepository.findAll());
    }

    
    @Transactional
    public UserResponse findById(Long id) throws NoSuchElementException {
        return UserResponse.from(userRepository.findById(id).orElseThrow(() -> new NoResourceException("User not found")));
    }

//    
//    public UserResponse add(UserRequest userRequest) {
//        User user = new User(userRequest.getUsername(), userRequest.getPassword(),
//                             userRequest.getEmail(), userRequest.getImage());
//        return UserResponse.from(userRepository.save(user));
//    }

    
    @Transactional
    public UserResponse update(UserRequest userRequest, Long id, String username) throws NoSuchElementException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NoResourceException("User not found"));
        if (!Objects.equals(user.getId(), id)) throw new NoAccessException();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setImage(userRequest.getImage());
        return UserResponse.from(userRepository.save(user));
    }

    
    @Transactional
    public void delete(Long id, String username, Boolean isAdmin) throws NoSuchElementException {
        User user = userRepository.findById(id).orElseThrow(() -> new NoResourceException("User not found"));
        if (!isAdmin && !user.getUsername().equals(username)) throw new NoAccessException();
        List<Review> reviews = user.getReviews();
        reviews.forEach(review -> {
            Movie movie = movieRepository.findById(review.getMovie().getId()).orElseThrow(() -> new NoResourceException("Movie not found"));
            movie.setRatingCount(movie.getRatingCount()-1);
            movie.setRatingSum(movie.getRatingSum()-review.getRating());
            movieRepository.save(movie);
        });
        userRepository.delete(user);
    }

    @Transactional
    public List<UserResponse> findAllAdmins() {
        return fromList(userRepository.findByRole(RolesEnum.ADMIN));
    }

    @Transactional
    public void addAdmin(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoResourceException("User not found"));
        user.setRole(RolesEnum.ADMIN);
        userRepository.save(user);
    }

    @Transactional
    public void removeAdmin(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoResourceException("User not found"));
        user.setRole(RolesEnum.USER);
        userRepository.save(user);
    }

    private List<UserResponse> fromList(List<User> users) {
        return users.stream().map(UserResponse::from).toList();
    }
}
