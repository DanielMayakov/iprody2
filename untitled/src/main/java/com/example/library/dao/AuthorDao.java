package com.example.library.dao;

import com.example.library.model.Author;

import java.util.List;

public interface AuthorDao {
    void create(Author author);
    Author read(int id);
    List<Author> readAll();
    void update(Author author);
    void delete(int id);
}
