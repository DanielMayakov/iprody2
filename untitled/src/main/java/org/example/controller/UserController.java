package org.example.controller;

import org.example.dao.JdbcUserDao;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private JdbcUserDao jdbcUserDao;

    // Получение списка всех пользователей и отображение их в user-list.jsp
    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", jdbcUserDao.findAll());
        return "user-list";
    }

    // Получение пользователя по ID и отображение его данных в user-details.jsp
    @GetMapping("/{id}")
    public String getUserById(@PathVariable("id") Integer id, Model model) {
        User user = jdbcUserDao.findById(id);
        if (user != null) {
            model.addAttribute("user", user);
            return "user-details"; // Страница для отображения информации о пользователе
        }
        model.addAttribute("error", "User not found with ID: " + id);
        return "error"; // Страница с сообщением об ошибке
    }

    // Переход на страницу добавления нового пользователя (user-add.jsp)
    @GetMapping("/add")
    public String getAddUserPage(Model model) {
        model.addAttribute("user", new User()); // Передаем пустой объект User для формы
        return "user-add";
    }

    // Обработка POST-запроса для добавления нового пользователя
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user) {
        jdbcUserDao.create(user);
        return "redirect:/users"; // Перенаправляем на список всех пользователей
    }

    // Переход на страницу редактирования пользователя (user-edit.jsp)
    @GetMapping("/edit/{id}")
    public String getEditUserPage(@PathVariable("id") Integer id, Model model) {
        User user = jdbcUserDao.findById(id);
        if (user != null) {
            model.addAttribute("user", user);
            return "user-edit"; // Страница для редактирования данных пользователя
        }
        model.addAttribute("error", "User not found with ID: " + id);
        return "error";
    }

    // Обработка POST-запроса для обновления данных пользователя
    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") Integer id, @ModelAttribute User updatedUser) {
        User existingUser = jdbcUserDao.findById(id);
        if (existingUser != null) {
            updatedUser.setId(id); // Устанавливаем ID, чтобы обновить существующего пользователя
            jdbcUserDao.update(updatedUser);
            return "redirect:/users"; // Перенаправляем на список всех пользователей
        }
        return "error";
    }

    // Удаление пользователя по ID
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        jdbcUserDao.delete(id);
        return "redirect:/users"; // Перенаправляем на список всех пользователей
    }
}

