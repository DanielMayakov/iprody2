package com.example.library;

import com.example.library.config.AppConfig;
import com.example.library.dao.AuthorDao;
import com.example.library.dao.BookDao;
import com.example.library.dao.JdbcAuthorDao;
import com.example.library.dao.JdbcBookDao;
import com.example.library.model.Author;
import com.example.library.model.Book;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class MainApp {

    public static void main(String[] args) {
        // Создаем контекст Spring
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Получаем JdbcTemplate из контекста
        AuthorDao authorDao = new JdbcAuthorDao(context.getBean("jdbcTemplate", org.springframework.jdbc.core.JdbcTemplate.class));
        BookDao bookDao = new JdbcBookDao(context.getBean("jdbcTemplate", org.springframework.jdbc.core.JdbcTemplate.class));

        // Добавление авторов
        Author author1 = new Author();
        author1.setName("George Orwell");
        author1.setCountry("United Kingdom");
        authorDao.create(author1);

        Author author2 = new Author();
        author2.setName("Fyodor Dostoevsky");
        author2.setCountry("Russia");
        authorDao.create(author2);

        // Получение авторов
        List<Author> authors = authorDao.readAll();
        System.out.println("Authors in the database:");
        for (Author author : authors) {
            System.out.println(author.getId() + ": " + author.getName() + " (" + author.getCountry() + ")");
        }

        // Добавление книг
        Book book1 = new Book();
        book1.setTitle("1984");
        book1.setPublishedYear(1949);
        book1.setAuthorId(authors.get(0).getId()); // Привязываем к первому автору (George Orwell)
        bookDao.create(book1);

        Book book2 = new Book();
        book2.setTitle("Crime and Punishment");
        book2.setPublishedYear(1866);
        book2.setAuthorId(authors.get(1).getId()); // Привязываем ко второму автору (Fyodor Dostoevsky)
        bookDao.create(book2);

        // Получение всех книг
        List<Book> books = bookDao.readAll();
        System.out.println("\nBooks in the database:");
        for (Book book : books) {
            System.out.println(book.getId() + ": " + book.getTitle() + " (" + book.getPublishedYear() + ")");
        }

        // Обновление книги
        System.out.println("\nUpdating the first book...");
        Book updatedBook = books.get(0);
        updatedBook.setTitle("Nineteen Eighty-Four");
        updatedBook.setPublishedYear(1950);
        bookDao.update(updatedBook);

        // Получение обновленной книги
        Book fetchedBook = bookDao.read(updatedBook.getId());
        System.out.println("Updated Book: " + fetchedBook.getTitle() + " (" + fetchedBook.getPublishedYear() + ")");

        // Удаление автора и связанных книг
        System.out.println("\nDeleting the first author...");
        authorDao.delete(authors.get(0).getId());

        // Проверка оставшихся авторов и книг
        authors = authorDao.readAll();
        books = bookDao.readAll();

        System.out.println("\nAuthors after deletion:");
        for (Author author : authors) {
            System.out.println(author.getId() + ": " + author.getName());
        }

        System.out.println("\nBooks after deletion:");
        for (Book book : books) {
            System.out.println(book.getId() + ": " + book.getTitle());
        }

        // Закрываем контекст Spring
        context.close();
    }
}
