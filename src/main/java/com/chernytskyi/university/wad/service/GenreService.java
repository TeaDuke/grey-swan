package com.chernytskyi.university.wad.service;

import com.chernytskyi.university.wad.domain.Genre;
import com.chernytskyi.university.wad.domain.dto.GenreRequest;
import com.chernytskyi.university.wad.domain.dto.GenreResponse;
import com.chernytskyi.university.wad.repository.GenreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GenreService implements RESTService<Genre, GenreRequest, GenreResponse> {

    @Autowired
    private GenreRepository genreRepository;

    @Override
    @Transactional
    public List<GenreResponse> findAll() {
        return fromList(genreRepository.findAll());
    }

    @Override
    @Transactional
    public GenreResponse findById(Long id) throws NoSuchElementException {
        return GenreResponse.from(genreRepository.findById(id).orElseThrow());
    }

    @Override
    @Transactional
    public GenreResponse add(GenreRequest genreRequest) {
        Genre genre = new Genre(genreRequest.getName(), genreRequest.getDescription(), getParentGenre(genreRequest));
        return GenreResponse.from(genreRepository.save(genre));
    }

    @Override
    @Transactional
    public GenreResponse update(GenreRequest genreRequest, Long id) throws NoSuchElementException {
        Genre genre = genreRepository.findById(id).orElseThrow();
        genre.setName(genreRequest.getName());
        genre.setDescription(genreRequest.getDescription());
        genre.setParentGenre(getParentGenre(genreRequest));
        return GenreResponse.from(genreRepository.save(genre));
    }

    @Override
    @Transactional
    public void delete(Long id) throws NoSuchElementException {
        Genre genre = genreRepository.findById(id).orElseThrow();
        genreRepository.delete(genre);
    }

    private Genre getParentGenre(GenreRequest genreRequest) {
        Genre parentGenre = null;
        if(genreRequest.getParentGenreId() != null)
            parentGenre = genreRepository.findById(genreRequest.getParentGenreId()).orElseThrow();
        return parentGenre;
    }

    private List<GenreResponse> fromList(List<Genre> genres) {
        return genres.stream().map(GenreResponse::from).toList();
    }
}
