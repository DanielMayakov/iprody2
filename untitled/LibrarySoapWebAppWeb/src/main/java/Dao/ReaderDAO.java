package Dao;

import Model.Reader;
import Dao.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReaderDAO {
    public List<Reader> getAllReaders() throws SQLException {
        List<Reader> readers = new ArrayList<>();
        String query = "SELECT * FROM readers";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Reader reader = new Reader();
                reader.setReaderId((int) resultSet.getLong("reader_id"));
                reader.setName(resultSet.getString("name"));
                reader.setEmail(resultSet.getString("email"));
                reader.setPhone(resultSet.getString("phone"));
                readers.add(reader);
            }
        }

        return readers;
    }

    public void addReader(Reader reader) throws SQLException {
        String query = "INSERT INTO readers (name, email, phone) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, reader.getName());
            preparedStatement.setString(2, reader.getEmail());
            preparedStatement.setString(3, reader.getPhone());
            preparedStatement.executeUpdate();
        }
    }
}
