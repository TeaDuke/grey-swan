package com.chernytskyi.university.wad.controller;

import com.chernytskyi.university.wad.domain.Movie;
import com.chernytskyi.university.wad.domain.dto.MovieRequest;
import com.chernytskyi.university.wad.domain.dto.MovieResponse;
import com.chernytskyi.university.wad.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping(value = "/movies", produces = "application/json")
    public List<MovieResponse> getMovies() {
        return movieService.findAll();
    }

    @GetMapping(value = "/movies/{id}", produces = "application/json")
    public MovieResponse getMovie(@PathVariable Long id) {
        return movieService.findById(id);
    }

    @PostMapping(value = "/movies",  produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public MovieResponse addMovie(@Valid @RequestBody MovieRequest movieRequest) {
        return movieService.add(movieRequest);
    }

    @PutMapping(value = "/movies/{id}",  produces = "application/json")
    public MovieResponse updateMovie(@Valid @RequestBody MovieRequest movieRequest, @PathVariable Long id) {
        return movieService.update(movieRequest, id);
    }

    @DeleteMapping(value = "/movies/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable Long id) {
        movieService.delete(id);
    }
}
