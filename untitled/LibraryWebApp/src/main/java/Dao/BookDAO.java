package Dao;

import Model.Book;
import java.sql.*;

public class BookDAO {
    public void addBook(Book book) throws SQLException {
        String sql = "INSERT INTO books (title, author, published_year, genre) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getPublishedYear());
            stmt.setString(4, book.getGenre());
            stmt.executeUpdate();
        }
    }

}
