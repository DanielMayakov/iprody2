package org.example.mapper;

import org.example.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserRowMapper implements RowMapper<User> {
    private final AddressRowMapper addressRowMapper;

    public UserRowMapper(AddressRowMapper addressRowMapper) {
        this.addressRowMapper = addressRowMapper;
    }

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("u_id"));
        user.setName(rs.getString("u_name"));
        user.setEmail(rs.getString("u_email"));
        user.setAddresses(new ArrayList<>());
        return user;
    }
}
