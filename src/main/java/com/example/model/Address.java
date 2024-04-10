package com.example.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("address")
public class Address {
    private String country;
    private Integer zipCode;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String province;
    private String postTown;
    private String village;
    private String neighborhood;
    private String apartment;
    private String poBox;
    private String prefecture;
    private String ward;
    private String district;

    public Address(String country, Integer zipCode, String address, String city,
            String state, String postalCode, String province, String postTown,
            String village, String neighborhood, String apartment, String poBox,
            String prefecture, String ward, String district) {
        super();
        this.country = country;
        this.zipCode = zipCode;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.province = province;
        this.postTown = postTown;
        this.village = village;
        this.neighborhood = neighborhood;
        this.apartment = apartment;
        this.poBox = poBox;
        this.prefecture = prefecture;
        this.ward = ward;
        this.district = district;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostTown() {
        return postTown;
    }

    public void setPostTown(String postTown) {
        this.postTown = postTown;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getPoBox() {
        return poBox;
    }

    public void setPoBox(String poBox) {
        this.poBox = poBox;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
