package com.chernytskyi.university.wad.repository;

import com.chernytskyi.university.wad.domain.Genre;
import com.chernytskyi.university.wad.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findByName(String name);
    List<Genre> findAllByMovies_Id(Long id); // TODO: check if it works
}
