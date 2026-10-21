package com.chernytskyi.university.wad.repository;

import com.chernytskyi.university.wad.domain.Movie;
import com.chernytskyi.university.wad.domain.Review;
import com.chernytskyi.university.wad.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByUser(User user);
    List<Review> findAllByMovie(Movie movie);
}
