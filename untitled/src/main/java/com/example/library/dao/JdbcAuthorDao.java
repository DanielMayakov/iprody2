package com.example.library.dao;

import com.example.library.model.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcAuthorDao implements AuthorDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAuthorDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Author author) {
        String sql = "INSERT INTO authors (name, country) VALUES (?, ?)";
        jdbcTemplate.update(sql, author.getName(), author.getCountry());
    }

    @Override
    public Author read(int id) {
        String sql = "SELECT * FROM authors WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new AuthorRowMapper(), id);
    }

    @Override
    public List<Author> readAll() {
        String sql = "SELECT * FROM authors";
        return jdbcTemplate.query(sql, new AuthorRowMapper());
    }

    @Override
    public void update(Author author) {
        String sql = "UPDATE authors SET name = ?, country = ? WHERE id = ?";
        jdbcTemplate.update(sql, author.getName(), author.getCountry(), author.getId());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM authors WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class AuthorRowMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = new Author();
            author.setId(rs.getInt("id"));
            author.setName(rs.getString("name"));
            author.setCountry(rs.getString("country"));
            return author;
        }
    }
}
