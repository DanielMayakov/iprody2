package com.example.library.dao;

import com.example.library.model.Book;

import java.util.List;

public interface BookDao {
    void create(Book book);
    Book read(int id);
    List<Book> readAll();
    void update(Book book);
    void delete(int id);
}
