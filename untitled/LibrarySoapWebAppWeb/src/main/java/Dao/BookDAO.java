package Dao;

import Model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Book book = new Book();
                book.setBookId((int) resultSet.getLong("book_id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPublishedYear(resultSet.getInt("published_year"));
                book.setGenre(resultSet.getString("genre"));
                books.add(book);
            }
        }

        return books;
    }

    public void addBook(Book book) throws SQLException {
        String query = "INSERT INTO books (title, author, published_year, genre) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setInt(3, book.getPublishedYear());
            preparedStatement.setString(4, book.getGenre());
            preparedStatement.executeUpdate();
        }
    }
}
