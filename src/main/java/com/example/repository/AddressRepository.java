package com.example.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Address;

@Repository
public interface AddressRepository extends MongoRepository<Address, String> {

    public List<Address> findByCountryAndAddress(String country,
            String address);
}