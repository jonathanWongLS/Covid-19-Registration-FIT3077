package com.example.application.data.entity.User;

import java.util.ArrayList;

/**
 * This is the User abstract class act as the base for all users
 */
public abstract class User {
    /**
     * User ID
     */
    private String id;

    /**
     * User first name
     */
    private String givenName;

    /**
     * User family name
     */
    private String familyName;

    /**
     * User username
     */
    private String userName;

    /**
     * User phone no
     */
    private String phoneNumber;

    /**
     * User additional information
     */
    private String additionalInfo;

    /**
     * Constructor of User with additional info
     * @param user_id user ID
     * @param userGivenName user's given name
     * @param userFamilyName user's family name
     * @param userName user's username
     * @param userPhoneNumber user's phone number
     * @param userAdditionalInfo additional information related to user
     */
    public User(String user_id, String userGivenName, String userFamilyName, String userName, String userPhoneNumber, String userAdditionalInfo) {
        this.id = user_id;
        this.givenName = userGivenName;
        this.familyName = userFamilyName;
        this.userName = userName;
        this.phoneNumber = userPhoneNumber;
        this.additionalInfo = userAdditionalInfo;
    }

    /**
     * Constructor of User without additional info
     * @param user_id user ID
     * @param userGivenName user's given name
     * @param userFamilyName user's family name
     * @param userName user's username
     * @param userPhoneNumber user's phone number
     */
    public User(String user_id, String userGivenName, String userFamilyName, String userName, String userPhoneNumber) {
        this.id = user_id;
        this.givenName = userGivenName;
        this.familyName = userFamilyName;
        this.userName = userName;
        this.phoneNumber = userPhoneNumber;
        this.additionalInfo = null;
    }

    /**
     * Getter for user's ID
     * @return user's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Getter for user's given name
     * @return user's given name
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * Getter for user's family name
     * @return user's family name
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Getter for user's username
     * @return user's username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Getter for user's phone number
     * @return user's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Getter for user's additional information
     * @return user's additional information
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * Abstract method to update users with new information
     * @param notification a notification to be added to receptionist's list of notifications
     * @param testingSiteIds a list of testingSiteIds to identify which user to update with notifications with
     * @throws Exception for Error in request
     */
    public abstract void update(String notification, ArrayList<String> testingSiteIds ) throws Exception;

    /**
     * prints out user's attributes in JSON formatted String
     * @return user's attributes in JSON formatted String
     */
    @Override
    public String toString() {
        return "User{\n" +
                "id='" + id + '\'' +
                ", \ngivenName='" + givenName + '\'' +
                ", \nfamilyName='" + familyName + '\'' +
                ", \nuserName='" + userName + '\'' +
                ", \nphoneNumber='" + phoneNumber + '\'' +
                ", \nadditionalInfo='" + additionalInfo + '\'' +
                "\n}";
    }
}
