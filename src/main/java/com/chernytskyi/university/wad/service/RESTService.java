package com.chernytskyi.university.wad.service;

import java.util.List;
import java.util.NoSuchElementException;

public interface RESTService<T, V, K> {
    List<K> findAll();
    K findById(Long id) throws NoSuchElementException;
    K add(V DTO);
    K update(V DTO, Long id) throws NoSuchElementException;
    void delete(Long id) throws NoSuchElementException;
}
