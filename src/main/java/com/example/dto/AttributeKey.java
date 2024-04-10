package com.example.dto;

public enum AttributeKey {

    ADDRESS("address", Itype.Text), ZIP_CODE("zipCode", Itype.Number),
    STATE("state", Itype.Text), CITY("city", Itype.Text),
    POSTAL_CODE("postalCode", Itype.Text),
    NEIGHBORHOOD("neighborhood", Itype.Text), PROVINCE("province", Itype.Text),
    PREFECTURE("prefecture", Itype.Text), DISTRICT("district", Itype.Text);

    private final String key;
    private final Itype type;

    AttributeKey(String key, Itype type) {
        this.key = key;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public Itype getType() {
        return type;
    }

    public static AttributeKey getByKey(String address) {
        for (AttributeKey attKey : AttributeKey.values()) {
            if (attKey.getKey().equalsIgnoreCase(address)) {
                return attKey;
            }
        }
        return null;
    }
}
