package com.chernytskyi.university.wad.domain.dto;

import com.chernytskyi.university.wad.domain.Genre;
import com.chernytskyi.university.wad.domain.Movie;

import java.util.Set;
import java.util.stream.Collectors;

public record GenreResponse(Long id, String name, String description, Long parentGenreId, Set<Long> movieIds) {
    public static GenreResponse from(Genre genre) {
        Set<Long> movieIds = genre.getMovies().stream().map(Movie::getId).collect(Collectors.toSet());
        return new GenreResponse(genre.getId(),
                genre.getName(),
                genre.getDescription(),
                genre.getParentGenre() == null ? null : genre.getParentGenre().getId(),
                movieIds
        );
    }
}
