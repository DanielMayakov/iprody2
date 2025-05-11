package org.example.mapper;

import org.example.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserResultSetExtractor implements ResultSetExtractor<List<User>> {
    private final AddressRowMapper addressRowMapper;
    private final UserRowMapper userRowMapper;

    public UserResultSetExtractor(AddressRowMapper addressRowMapper, UserRowMapper userRowMapper) {
        this.addressRowMapper = addressRowMapper;
        this.userRowMapper = userRowMapper;
    }


    @Override
    public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<User> users = new ArrayList<>();

        User currentUser = null;

        while(rs.next()) {
            int userId = rs.getInt("u_id");
            if(currentUser == null || userId != currentUser.getId()) {
                currentUser = userRowMapper.mapRow(rs, rs.getRow());
                users.add(currentUser);
            }

            Integer value = rs.getObject("a_id", Integer.class);
            if(value != null)
                currentUser.getAddresses().add(addressRowMapper.mapRow(rs, rs.getRow()));
        }
        return users;
    }
}
