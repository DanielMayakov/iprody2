package com.example.library.dao;

import com.example.library.model.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcBookDao implements BookDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcBookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Book book) {
        String sql = "INSERT INTO books (title, published_year, author_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, book.getTitle(), book.getPublishedYear(), book.getAuthorId());
    }

    @Override
    public Book read(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BookRowMapper(), id);
    }

    @Override
    public List<Book> readAll() {
        String sql = "SELECT * FROM books";
        return jdbcTemplate.query(sql, new BookRowMapper());
    }

    @Override
    public void update(Book book) {
        String sql = "UPDATE books SET title = ?, published_year = ?, author_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, book.getTitle(), book.getPublishedYear(), book.getAuthorId(), book.getId());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setTitle(rs.getString("title"));
            book.setPublishedYear(rs.getInt("published_year"));
            book.setAuthorId(rs.getInt("author_id"));
            return book;
        }
    }
}
