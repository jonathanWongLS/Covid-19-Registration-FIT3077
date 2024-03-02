package com.example.application.data.entity.User;

import java.util.ArrayList;

/**
 * This is the children HealthcareWorker class extending User abstract class
 */
public class HealthcareWorker extends User{

    /**
     * Constructor with additional info
     * @param user_id user ID
     * @param userGivenName user's given name
     * @param userFamilyName user's family name
     * @param userName user's username
     * @param userPhoneNumber user's phone number
     * @param userAdditionalInfo additional information related to customer
     */
    public HealthcareWorker(String user_id, String userGivenName, String userFamilyName, String userName, String userPhoneNumber, String userAdditionalInfo) {
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
    public HealthcareWorker(String user_id, String userGivenName, String userFamilyName, String userName, String userPhoneNumber) {
        super(user_id, userGivenName, userFamilyName, userName, userPhoneNumber);
    }

    /**
     *
     * @param notification a notification to be added to healthcare worker's list of notifications
     * @param testingSiteIds a list of testingSiteIds to identify which healthcare workers' notifications are to be updated
     */
    public void update(String notification, ArrayList<String> testingSiteIds) {}
}
