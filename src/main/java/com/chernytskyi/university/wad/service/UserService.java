package com.chernytskyi.university.wad.service;

import com.chernytskyi.university.wad.domain.User;
import com.chernytskyi.university.wad.domain.dto.UserRequest;
import com.chernytskyi.university.wad.domain.dto.UserResponse;
import com.chernytskyi.university.wad.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService implements RESTService<User, UserRequest, UserResponse> {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public List<UserResponse> findAll() {
        return fromList(userRepository.findAll());
    }

    @Override
    @Transactional
    public UserResponse findById(Long id) throws NoSuchElementException {
        return UserResponse.from(userRepository.findById(id).orElseThrow());
    }

    @Override
    public UserResponse add(UserRequest userRequest) {
        User user = new User(userRequest.getUsername(), userRequest.getPassword(),
                             userRequest.getEmail(), userRequest.getImage());
        return UserResponse.from(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse update(UserRequest userRequest, Long id) throws NoSuchElementException {
        User user = userRepository.findById(id).orElseThrow();
        user.setUsername(userRequest.getUsername());
//        user.setPassword(userDTO.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setImage(userRequest.getImage());
        return UserResponse.from(userRepository.save(user));
    }

    @Override
    @Transactional
    public void delete(Long id) throws NoSuchElementException {
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
    }

    private List<UserResponse> fromList(List<User> users) {
        return users.stream().map(UserResponse::from).toList();
    }
}
