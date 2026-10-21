package com.chernytskyi.university.wad.domain.dto;

import com.chernytskyi.university.wad.domain.Genre;
import com.chernytskyi.university.wad.domain.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record MovieResponse(Long id, String title, byte[] cover, String director, Integer duration, Integer releaseYear, Double rating, Set<Long> genreIds) {
    public static MovieResponse from(Movie movie) {
        Double rating = movie.getRatingCount() == 0 ? 0 : movie.getRatingSum()/(double)movie.getRatingCount();
        Set<Long> genreIds = movie.getGenres().stream().map(Genre::getId).collect(Collectors.toSet());
        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getCover(),
                movie.getDirector(),
                movie.getDuration(),
                movie.getReleaseYear(),
                rating,
                genreIds
        );
    }
}
