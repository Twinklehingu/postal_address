package com.example.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.model.AddressFormat;

@Repository
public interface AddressFormatRespository
        extends MongoRepository<AddressFormat, String> {

    @Query("{country:'?0'}")
    public AddressFormat findAddressFormatByCountry(String country);

    public List<AddressFormat> findAll();

}