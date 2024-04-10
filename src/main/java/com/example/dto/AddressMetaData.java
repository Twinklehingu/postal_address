package com.example.dto;

import java.util.HashMap;
import java.util.Map;

public class AddressMetaData {
    String country;
    Map<String, String> map; // components of Address

    public AddressMetaData(String country) {
        this.country = country;
        this.map = new HashMap<String, String>();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

}