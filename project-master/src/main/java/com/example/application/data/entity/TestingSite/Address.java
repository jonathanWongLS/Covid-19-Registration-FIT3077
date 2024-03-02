package com.example.application.data.entity.TestingSite;

/**
 * This is the helper class Address representing the address of testing site data from API
 */
public class Address {
    private double latitude;
    private double longitude;
    private int unitNumber;
    private String street;
    private String street2;
    private String suburb;
    private String state;
    private String postcode;

    /**
     * Constructor of Address
     * @param latitude latitude of site
     * @param longitude longitude of site
     * @param unitNumber site unitNumber
     * @param street site street
     * @param street2 site street2
     * @param suburb site suburb
     * @param state site state
     * @param postcode site postcode
     */
    public Address(double latitude, double longitude, int unitNumber, String street, String street2, String suburb, String state, String postcode) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.unitNumber = unitNumber;
        this.street = street;
        this.street2 = street2;
        this.suburb = suburb;
        this.state = state;
        this.postcode = postcode;
    }

    /**
     * Getter
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Getter
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Getter
     * @return unit number
     */
    public int getUnitNumber() {
        return unitNumber;
    }

    /**
     * Getter
     * @return street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Getter
     * @return street2
     */
    public String getStreet2() {
        return street2;
    }

    /**
     * Getter
     * @return street+street2 / street2
     */
    public String getStreets() {
        if (street2 == "null"){
            return street;
        }
        else {
            return street + " "+ street2;
        }
    }

    /**
     * Getter
     * @return suburb
     */
    public String getSuburb() {
        return suburb;
    }

    /**
     * Getter
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * Getter
     * @return postcode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * toString method
     * @return String form of the class
     */
    @Override
    public String toString() {
        if (street2 == "null"){
            return street + ", " +
                    postcode + ", " +
                    suburb + ", " +
                    state;
        }
        else {
            return street + " "+ street2 + ", " +
                    postcode + ", " +
                    suburb + ", " +
                    state;
        }
    }
}
