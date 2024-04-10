package com.example.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("addressFormat")
public class AddressFormat {
    private String country;

    private List<String> formats;

    public AddressFormat(String country, List<String> formats) {
        super();
        this.country = country;
        this.formats = formats;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getFormats() {
        return formats;
    }

    public void setFormats(List<String> formats) {
        this.formats = formats;
    }

}
