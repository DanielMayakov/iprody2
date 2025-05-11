package org.example.mapper;

import org.example.model.Address;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressRowMapper implements RowMapper<Address> {
    @Override
    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
        Address address = new Address();
        address.setId(rs.getInt("a_id"));
        address.setStreet(rs.getString("a_street"));
        address.setCity(rs.getString("a_city"));
        address.setPostal_code(rs.getString("a_postal_code"));
        address.setUser_id(rs.getInt("a_user_id"));
        return address;
    }
}
