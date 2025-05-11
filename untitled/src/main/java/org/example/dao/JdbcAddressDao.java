package org.example.dao;

import org.example.model.Address;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcAddressDao<T> implements AddressDao{
    private final JdbcTemplate template;

    @Autowired
    public JdbcAddressDao(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Address> findAll() {
        String sql = "Select * from addresses";
        return template.query(sql, new BeanPropertyRowMapper<>(Address.class));
    }

    @Override
    public Address findById(int id) {
        String sql = "select * from addresses where id = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Address.class), id);
    }

    @Override
    public Address findByUserId(int id) {
        String sql = "select * from addresses where user_id = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Address.class), id);
    }

    @Override
    public void create(Address object) {
        String sql = "insert into addresses (user_id, city, street, postal_code) values (?,?,?,?)";
        template.update(sql, object.getUser_id(), object.getCity(), object.getStreet(), object.getPostal_code());
    }

    @Override
    public void update(Address object) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void create(List<Address> objects) {

    }
}
