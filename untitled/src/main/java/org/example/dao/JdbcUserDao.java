package org.example.dao;

import org.example.mapper.AddressRowMapper;
import org.example.mapper.UserResultSetExtractor;
import org.example.mapper.UserRowMapper;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcUserDao implements UserDao {
    private final JdbcTemplate template;
    private final UserRowMapper userRowMapper;
    private final UserResultSetExtractor extractor;

    @Autowired
    public JdbcUserDao(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
        this.userRowMapper = new UserRowMapper(new AddressRowMapper());
        this.extractor = new UserResultSetExtractor(new AddressRowMapper(), userRowMapper);
    }

    @Override
    public List<User> findAll() {
        String sql = "select u.id as u_id, u.name as u_name, u.email as u_email," +
                "a.id as a_id, a.city as a_city, a.street as a_street, a.user_id as a_user_id, a.postal_code as a_postal_code " +
                "from users u left join addresses a on a.user_id = u.id order by u_id";
        return template.query(sql, extractor);
    }

    @Override
    public User findById(int id) {
        String sql = "select u.id as u_id, u.name as u_name, u.email as u_email," +
                "a.id as a_id, a.city as a_city, a.street as a_street, a.user_id as a_user_id, a.postal_code as a_postal_code " +
                "from users u left join addresses a on a.user_id = u.id where u.id = ?";
        return template.query(sql, extractor, id).stream().findFirst().orElse(null);
    }


    @Override
    public void create(User user) {
        String sql = "insert into users (name, email) values (?,?)";
        template.update(sql, user.getName(), user.getEmail());
    }

    @Override
    public void update(User user) {
        String sql = "update users set name = ?, email = ? where id = ?";
        template.update(sql, user.getName(), user.getEmail(), user.getId());
    }

    @Override
    public void delete(int id) {
        String sql = "delete from users where id = ?";
        template.update(sql, id);
    }

    @Override
    public void create(List<User> users) {
        String sql = "insert into users (name, email) values (?,?)";

        template.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, users.get(i).getName());
                ps.setString(2, users.get(i).getEmail());
            }

            @Override
            public int getBatchSize() {
                return users.size();
            }
        });
    }
}
