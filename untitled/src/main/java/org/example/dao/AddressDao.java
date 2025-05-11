package org.example.dao;

import org.example.model.Address;

public interface AddressDao extends Dao<Address> {
    Address findByUserId(int id);
}
