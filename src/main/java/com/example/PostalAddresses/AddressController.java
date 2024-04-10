package com.example.PostalAddresses;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;
import java.util.Set;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.AddressComponent;
import com.example.dto.AddressMetaData;
import com.example.dto.AttributeKey;
import com.example.dto.Itype;
import com.example.model.Address;
import com.example.model.AddressFormat;
import com.example.repository.AddressFormatRespository;
import com.example.repository.AddressRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;

@RestController
public class AddressController {
    @Value("${valid.countries}")
    private Set<String> validCountries;

    private final AddressFormatRespository addressFormatRepo;
    private final MongoOperations mongoOps;
    private MongoClient mongoClient;

    AddressController(AddressFormatRespository addressFormatRepo,
            AddressRepository addressRepo) {
        this.addressFormatRepo = addressFormatRepo;
        this.mongoOps = new MongoTemplate(new SimpleMongoClientDbFactory(
                MongoClients.create(), "addressFormat"));
        createIndexes();
    }

    private void createIndexes() {
        this.mongoClient = MongoClients
                .create("mongodb://localhost:27017/addressFormat");
        MongoDatabase database = mongoClient.getDatabase("addressFormat");
        MongoCollection<Document> collection = database
                .getCollection("address");

        IndexOptions partialFilterIndexOptions = new IndexOptions()
                .partialFilterExpression(Filters.exists("address.country"));
        collection.createIndex(Indexes.descending("country"),
                partialFilterIndexOptions);
    }

    @GetMapping("/address/addressformat")
    public ResponseEntity<AddressMetaData> getAddressFormatbyCountry(
            @RequestParam(value = "country") String country) {

        if (!validCountries.contains(country))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        AddressFormat format = addressFormatRepo
                .findAddressFormatByCountry(country);

        AddressMetaData metaData = new AddressMetaData(country);

        for (String addressFromat : format.getFormats()) {
            AttributeKey attKey = AttributeKey.getByKey(addressFromat);

            if (attKey != null) {
                metaData.getMap().put(attKey.getKey(),
                        attKey.getType().toString());
            }
        }

        return ResponseEntity.ok(metaData);
    }

    @GetMapping("/address/search/country")
    public ResponseEntity<List<Address>> searchByCountryAndAddress(
            @RequestParam(value = "country") String country,
            @RequestBody List<AddressComponent> addressComponenets) {

        if (!validCountries.contains(country)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Criteria c = where("country").is(country);

        for (AddressComponent addressComponenet : addressComponenets) {

            AttributeKey metadata = AttributeKey
                    .getByKey(addressComponenet.getField());

            if (metadata.getType().equals(Itype.Number)) {

                try {
                    c.and(addressComponenet.getField())
                            .is(Integer.valueOf(addressComponenet.getValue()));
                } catch (NumberFormatException e) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } else {
                c.and(addressComponenet.getField())
                        .is(addressComponenet.getValue());
            }
        }

        List<Address> addressFromDb = mongoOps.find(query(c), Address.class);

        return addressFromDb != null ? ResponseEntity.ok(addressFromDb)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/address/search")
    public ResponseEntity<List<Address>> searchByAddress(
            @RequestBody List<AddressComponent> addressComponenets) {
        Criteria c = null;
        boolean isNull = true;

        for (AddressComponent addressComponenet : addressComponenets) {
            AttributeKey metadata = AttributeKey
                    .getByKey(addressComponenet.getField());

            Integer valueField = null;

            if (metadata.getType().equals(Itype.Number)) {
                valueField = Integer.valueOf(addressComponenet.getValue());
            }

            if (isNull && valueField != null) {

                c = where(addressComponenet.getField()).is(valueField);
                isNull = false;

            } else if (!isNull && valueField != null) {

                c.and(addressComponenet.getField()).is(valueField);

            } else if (isNull && valueField == null) {

                c = where(addressComponenet.getField())
                        .is(addressComponenet.getValue());
                isNull = false;
            } else {

                c.and(addressComponenet.getField())
                        .is(addressComponenet.getValue());
            }
        }

        List<Address> addressFromDb = mongoOps.find(query(c), Address.class);
        return addressFromDb != null ? ResponseEntity.ok(addressFromDb)
                : ResponseEntity.notFound().build();
    }
}
