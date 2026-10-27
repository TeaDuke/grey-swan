package com.chernytskyi.university.wad.controller;

import com.chernytskyi.university.wad.domain.Genre;
import com.chernytskyi.university.wad.domain.dto.GenreRequest;
import com.chernytskyi.university.wad.domain.dto.GenreResponse;
import com.chernytskyi.university.wad.service.GenreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class GenreController {

    @Autowired
    private GenreService genreService;

    @GetMapping(value = "/genres", produces = "application/json")
    public List<GenreResponse> getGenres() {
        return genreService.findAll();
    }

    @GetMapping(value = "/genres/{id}", produces = "application/json")
    public GenreResponse getGenre(@PathVariable Long id) {
        return genreService.findById(id);
    }

    @PostMapping(value = "/genres",  produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public GenreResponse addGenre(@Valid @RequestBody GenreRequest genreRequest) {
        return genreService.add(genreRequest);
    }

    @PutMapping(value = "/genres/{id}",  produces = "application/json")
    public GenreResponse updateGenre(@Valid @RequestBody GenreRequest genreRequest, @PathVariable Long id) {
        return genreService.update(genreRequest, id);
    }

    @PatchMapping(value = "/genres/{id}")
    public GenreResponse updateGenrePatch(@RequestBody GenreRequest genreRequest, @PathVariable Long id) {
        return genreService.update(genreRequest, id);
    }

    @DeleteMapping(value = "/genres/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGenre(@PathVariable Long id) {
        genreService.delete(id);
    }
}
