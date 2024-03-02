package com.example.application.data.entity.User;

import java.util.ArrayList;

/**
 * This is the children Customer class extending User abstract class
 */
public class Customer extends User{

    /**
     * Constructor with additional info
     * @param user_id user ID
     * @param userGivenName user's given name
     * @param userFamilyName user's family name
     * @param userName user's username
     * @param userPhoneNumber user's phone number
     * @param userAdditionalInfo additional information related to customer
     */
    public Customer(String user_id, String userGivenName, String userFamilyName, String userName, String userPhoneNumber, String userAdditionalInfo) {
        super(user_id, userGivenName, userFamilyName, userName, userPhoneNumber, userAdditionalInfo);
    }

    /**
     * Constructor without additional info
     * @param user_id user ID
     * @param userGivenName user's given name
     * @param userFamilyName user's family name
     * @param userName user's username
     * @param userPhoneNumber user's phone number
     */
    public Customer(String user_id, String userGivenName, String userFamilyName, String userName, String userPhoneNumber) {
        super(user_id, userGivenName, userFamilyName, userName, userPhoneNumber);
    }

    /**
     * An unimplemented method that can be used by Customers to update with new notification in the future
     * @param notification a notification to be added to customer's list of notifications
     * @param testingSiteIds a list of testingSiteIds to identify which customers' notifications are to be updated
     */
    @Override
    public void update(String notification, ArrayList<String> testingSiteIds) {}
}
