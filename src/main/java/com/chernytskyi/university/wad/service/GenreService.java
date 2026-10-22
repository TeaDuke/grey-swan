package com.chernytskyi.university.wad.service;

import com.chernytskyi.university.wad.domain.Genre;
import com.chernytskyi.university.wad.domain.dto.GenreRequest;
import com.chernytskyi.university.wad.domain.dto.GenreResponse;
import com.chernytskyi.university.wad.exception.NoResourceException;
import com.chernytskyi.university.wad.repository.GenreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return GenreResponse.from(genreRepository.findById(id).orElseThrow(() -> new NoResourceException("Genre not found")));
    }

    @Override
    @Transactional
    public GenreResponse add(GenreRequest genreRequest) {
        Genre genre = new Genre(genreRequest.getName(), genreRequest.getDescription());
        return GenreResponse.from(genreRepository.save(genre));
    }

    @Override
    @Transactional
    public GenreResponse update(GenreRequest genreRequest, Long id) throws NoSuchElementException {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new NoResourceException("Genre not found"));
        genre.setName(genreRequest.getName());
        genre.setDescription(genreRequest.getDescription());
        return GenreResponse.from(genreRepository.save(genre));
    }

    @Override
    @Transactional
    public void delete(Long id) throws NoSuchElementException {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new NoResourceException("Genre not found"));
        genreRepository.delete(genre);
    }

    private List<GenreResponse> fromList(List<Genre> genres) {
        return genres.stream().map(GenreResponse::from).toList();
    }
}
