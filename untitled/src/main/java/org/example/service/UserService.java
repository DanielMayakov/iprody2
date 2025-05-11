package org.example.service;

import org.example.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User getById(int id); // Новый метод
    void create(User user);
    void update(User user);
    void delete(int id);
    void create(List<User> users);
}
