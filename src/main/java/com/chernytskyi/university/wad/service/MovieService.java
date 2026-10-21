package com.chernytskyi.university.wad.service;

import com.chernytskyi.university.wad.domain.Genre;
import com.chernytskyi.university.wad.domain.Movie;
import com.chernytskyi.university.wad.domain.dto.GenreResponse;
import com.chernytskyi.university.wad.domain.dto.MovieRequest;
import com.chernytskyi.university.wad.domain.dto.MovieResponse;
import com.chernytskyi.university.wad.repository.GenreRepository;
import com.chernytskyi.university.wad.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieService implements RESTService<Movie, MovieRequest, MovieResponse> {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private GenreRepository genreRepository;

    @Override
    @Transactional
    public List<MovieResponse> findAll() {
        return fromList(movieRepository.findAll());
    }

    @Override
    @Transactional
    public MovieResponse findById(Long id) throws NoSuchElementException {
        return MovieResponse.from(movieRepository.findById(id).orElseThrow());
    }

    @Override
    public MovieResponse add(MovieRequest movieRequest) {
        Movie movie = new Movie(movieRequest.getTitle(), movieRequest.getCover(), movieRequest.getDirector(),
                                movieRequest.getDuration(), movieRequest.getReleaseYear(), getGenres(movieRequest.getGenreIds()));
        return MovieResponse.from(movieRepository.save(movie));
    }

    @Override
    @Transactional
    public MovieResponse update(MovieRequest movieRequest, Long id) throws NoSuchElementException {
        Movie movie = movieRepository.findById(id).orElseThrow();
        movie.setTitle(movieRequest.getTitle());
        movie.setCover(movieRequest.getCover());
        movie.setDirector(movieRequest.getDirector());
        movie.setDuration(movieRequest.getDuration());
        movie.setReleaseYear(movieRequest.getReleaseYear());
        movie.setGenres(getGenres(movieRequest.getGenreIds()));
        return MovieResponse.from(movieRepository.save(movie));
    }

    @Override
    @Transactional
    public void delete(Long id) throws NoSuchElementException {
        Movie movie = movieRepository.findById(id).orElseThrow();
        movieRepository.delete(movie);
    }

    private Set<Genre> getGenres(Set<Long> genreIds) {
        return genreIds.stream().map(g -> genreRepository.findById(g).orElseThrow()).collect(Collectors.toSet());
    }

    private List<MovieResponse> fromList(List<Movie> movies) {
        return movies.stream().map(MovieResponse::from).toList();
    }

}
