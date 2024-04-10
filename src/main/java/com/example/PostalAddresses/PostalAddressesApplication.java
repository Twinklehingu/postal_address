package com.example.PostalAddresses;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.example.model.Address;
import com.example.model.AddressFormat;
import com.example.repository.AddressFormatRespository;
import com.example.repository.AddressRepository;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableMongoRepositories("com.example.repository")
public class PostalAddressesApplication implements CommandLineRunner {

    @Autowired
    AddressFormatRespository addressFormatRepo;

    @Autowired
    AddressRepository addressRepo;

    public static void main(String[] args) {
        SpringApplication.run(PostalAddressesApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Application started");

        this.addressFormatRepo.deleteAll();
        addAddressFormats("address_formats.json");
        this.addressRepo.deleteAll();

        addAddresses("US", "us_addresses.json");
        addAddresses("Mexico", "Mexico_addresses.json");
        addAddresses("Brazil", "Brazil_addresses.json");
        addAddresses("Germany", "Germany_addresses.json");
        addAddresses("Canada", "Canada_addresses.json");
        addAddresses("UK", "UK_addresses.json");
        addAddresses("India", "India_addresses.json");
        addAddresses("South Korea", "South_Korea_addresses.json");
        addAddresses("Spain", "Spain_addresses.json");
        addAddresses("Japan", "Japan_addresses.json");

        new AddressController(addressFormatRepo, addressRepo);
    }

    private void addAddressFormats(String filename) {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(
                    new FileReader(new ClassPathResource(filename).getFile()));
            JSONArray jsonArray = (JSONArray) obj;

            for (Object object : jsonArray) {
                JSONObject jsonObj = (JSONObject) object;

                String country = (String) jsonObj.get("country");
                List<String> formats = (List<String>) jsonObj.get("formats");

                AddressFormat addressFormatModel = new AddressFormat(country,
                        formats);

                this.addressFormatRepo.save(addressFormatModel);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void addAddresses(String country, String filename) {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(
                    new FileReader(new ClassPathResource(filename).getFile()));
            JSONArray jsonArray = (JSONArray) obj;

            for (Object object : jsonArray) {
                JSONObject jsonObj = (JSONObject) object;

                String zipCodeString = (String) jsonObj.get("zipCode");
                Integer zipCode = zipCodeString != null
                        ? Integer.parseInt(zipCodeString)
                        : null;
                String address = (String) jsonObj.get("address");
                String city = (String) jsonObj.get("city");
                String state = (String) jsonObj.get("state");
                String postalCode = (String) jsonObj.get("postalCode");
                String province = (String) jsonObj.get("province");
                String postTown = (String) jsonObj.get("postTown");
                String village = (String) jsonObj.get("village");
                String neighborhood = (String) jsonObj.get("neighborhood");
                String apartment = (String) jsonObj.get("apartment");
                String poBox = (String) jsonObj.get("poBox");
                String prefecture = (String) jsonObj.get("prefecture");
                String ward = (String) jsonObj.get("ward");
                String district = (String) jsonObj.get("district");

                Address addressModel = new Address(country, zipCode, address,
                        city, state, postalCode, province, postTown, village,
                        neighborhood, apartment, poBox, prefecture, ward,
                        district);

                this.addressRepo.save(addressModel);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
