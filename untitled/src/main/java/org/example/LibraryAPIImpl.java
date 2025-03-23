package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryAPIImpl implements LibraryAPI {

    @Override
    public Book addBook(Book book) {
        String sql = "INSERT INTO books (title, author, published_year, genre) VALUES (?, ?, ?, ?) RETURNING book_id";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getPublishedYear());
            statement.setString(4, book.getGenre());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book.setId(resultSet.getInt("book_id"));
            }

            return book;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding book");
        }
    }

    @Override
    public Book updateBookStatus(int bookId, String status) {
        String sql = "UPDATE books SET status = ? WHERE book_id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setInt(2, bookId);
            statement.executeUpdate();

            return getBookById(bookId);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating book status");
        }
    }


    @Override
    public Reader addReader(Reader reader) {
        String sql = "INSERT INTO readers (name, email, phone) VALUES (?, ?, ?) RETURNING reader_id";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, reader.getName());
            statement.setString(2, reader.getEmail());
            statement.setString(3, reader.getPhone());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                reader.setId(resultSet.getInt("reader_id"));
            }

            return reader;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding reader");
        }
    }

    @Override
    public List<OccupiedBook> getOccupiedBooks() {
        String sql = "SELECT b.*, r.* FROM borrowed_books bb " +
                "JOIN books b ON bb.book_id = b.book_id " +
                "JOIN readers r ON bb.reader_id = r.reader_id " +
                "WHERE bb.status = 'borrowed'";

        List<OccupiedBook> occupiedBooks = new ArrayList<>();

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getInt("book_id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("published_year"),
                        resultSet.getString("genre"),
                        resultSet.getString("status")
                );

                Reader reader = new Reader(
                        resultSet.getInt("reader_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone")
                );

                occupiedBooks.add(new OccupiedBook(book, reader));
            }

            return occupiedBooks;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching occupied books");
        }
    }

    @Override
    public Reader updateReaderInfo(Reader reader) {
        String sql = "UPDATE readers SET name = ?, email = ?, phone = ? WHERE reader_id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, reader.getName());
            statement.setString(2, reader.getEmail());
            statement.setString(3, reader.getPhone());
            statement.setInt(4, reader.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                return reader; // Возвращаем обновленного читателя
            } else {
                throw new RuntimeException("Reader not found with ID: " + reader.getId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating reader info");
        }
    }


    @Override
    public List<Book> filterBooksByStatus(String status) {
        String sql = "SELECT * FROM books WHERE status = ?";
        List<Book> books = new ArrayList<>();

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getInt("book_id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("published_year"),
                        resultSet.getString("genre"),
                        resultSet.getString("status")
                );
                books.add(book);
            }

            return books;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error filtering books by status");
        }
    }


    @Override
    public List<Book> findBooksBorrowedAfterDate(String date) {
        String sql = "SELECT b.* FROM borrowed_books bb " +
                "JOIN books b ON bb.book_id = b.book_id " +
                "WHERE bb.borrow_date > ?";
        List<Book> books = new ArrayList<>();

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, java.sql.Date.valueOf(date)); // Преобразуем строку в SQL-дату
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getInt("book_id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("published_year"),
                        resultSet.getString("genre"),
                        resultSet.getString("status")
                );
                books.add(book);
            }

            return books;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error finding books borrowed after date");
        }
    }

    private Book getBookById(int bookId) {
        String sql = "SELECT * FROM books WHERE book_id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Book(
                        resultSet.getInt("book_id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("published_year"),
                        resultSet.getString("genre"),
                        resultSet.getString("status")
                );
            } else {
                throw new RuntimeException("Book not found with ID: " + bookId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching book by ID");
        }
    }

}

