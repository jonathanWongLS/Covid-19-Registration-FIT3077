package com.example.application.data.entity.TestingSite;

/**
 * This is the TestingSite class representing the testing site data from API
 */
public class TestingSite {
    private String id;
    private String name;
    private String description;
    private String websiteUrl;
    private String phoneNumber;
    private Address address;
    private String facilityType;
    private String openTime;
    private String closeTime;
    private String operationTime;
    private String waitingTime;

    /**
     * Constructor for TestingSite
     * @param id testing site id
     * @param name testing site name
     * @param description testing site description
     * @param websiteUrl testing site websiteUrl
     * @param phoneNumber testing site phoneNumber
     * @param latitude latitude of site
     * @param longitude longitude of site
     * @param unitNumber site unitNumber
     * @param street site street
     * @param street2 site street2
     * @param suburb site suburb
     * @param state site state
     * @param postcode site postcode
     * @param facilityType type of testing site
     * @param openTime open time of testing site
     * @param closeTime close time of testing site
     * @param waitingTime waiting time of testing site
     */
    public TestingSite(String id, String name, String description, String websiteUrl, String phoneNumber, double latitude, double longitude, int unitNumber, String street, String street2, String suburb, String state, String postcode, String facilityType, String openTime, String closeTime, String waitingTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.websiteUrl = websiteUrl;
        this.phoneNumber = phoneNumber;
        this.address = new Address(latitude, longitude, unitNumber, street, street2, suburb, state, postcode);
        this.facilityType = facilityType;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.operationTime = openTime + " - " + closeTime;
        this.waitingTime = waitingTime;
    }

    /**
     * Getter
     * @return testing site id
     */
    public String getId() {
        return id;
    }

    /**
     * Getter
     * @return testing site name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter
     * @return testing site description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter
     * @return testing site website URL
     */
    public String getWebsiteUrl() {
        return websiteUrl;
    }

    /**
     * Getter
     * @return testing site phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Getter
     * @return testing site address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Getter
     * @return type of testing site
     */
    public String getFacilityType() {
        return facilityType;
    }

    /**
     * Getter
     * @return open time of testing site to close time of testing site
     */
    public String getOperationTime() {
        return openTime + " - " + closeTime;
    }

    /**
     * Getter
     * @return open time of testing site
     */
    public String getOpenTime() {
        return openTime;
    }

    /**
     * Getter
     * @return close time of testing site
     */
    public String getCloseTime() {
        return closeTime;
    }

    /**
     * Getter
     * @return waiting time of testing site
     */
    public String getWaitingTime() {
        return waitingTime;
    }

    /**
     * Setter of Waiting Time
     */
    public void setWaitingTime(String waitingTime){
        this.waitingTime = waitingTime;
    }

    /**
     * Setter of testing site id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Setter of testing site name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * toString method
     * @return String form of the class
     */
    @Override
    public String toString() {
        return "TestingSite{" +
                "\nid= '" + id + '\'' +
                ", \nname= '" + name + '\'' +
                ", \ndescription= '" + description + '\'' +
                ", \nwebsiteUrl= '" + websiteUrl + '\'' +
                ", \nphoneNumber= '" + phoneNumber + '\'' +
                ", \naddress=" + address.toString() +
                ", \nfacilityType= '" + facilityType + '\'' +
                ", \noperationTime= '" + operationTime + '\'' +
                ", \nwaitingTime= '" + waitingTime + '\'' +
                '}';
    }

    /**
     * getInfo method
     * @return String form of the class without some attribute
     */
    public String getInfo(){
        return "TestingSite{" +
                "\nid= '" + id + '\'' +
                ", \nname= '" + name + '\'' +
                ", \nphoneNumber= '" + phoneNumber + '\'' +
                ", \naddress= '" + address.toString() + '\'' +
                ", \nfacilityType= '" + facilityType + '\'' +
                ", \noperationTime= '" + operationTime + '\'' +
                ", \nwaitingTime= '" + waitingTime + '\'' +
                "\n}";
    }
}