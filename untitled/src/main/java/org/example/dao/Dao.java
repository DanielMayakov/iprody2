package org.example.dao;


import java.util.List;

public interface Dao<T> {
    List<T> findAll();
    T findById(int id);
    void create(T object);
    void update(T object);
    void delete(int id);
    void create(List<T> objects);
}
