package com.example.application.data.entity.User;

import com.example.application.data.entity.HttpHelper;

import java.util.ArrayList;

/**
 * This is the children Receptionist class extending User abstract class
 */
public class Receptionist extends User {

    /**
     * ID of the Testing site the receptionist works at
     */
    private String testingSiteId;

    /**
     * List of notifications for receptionist
     */
    private ArrayList<String> notifications;

    /**
     * Constructor for Receptionist
     * @param user_id user ID
     * @param userGivenName user's given name
     * @param userFamilyName user's family name
     * @param userName user's username
     * @param userPhoneNumber user's phone number
     * @param testingSiteId ID of the Testing site the receptionist works at
     * @param notifications list of notifications for receptionist
     */
    public Receptionist(String user_id, String userGivenName, String userFamilyName, String userName, String userPhoneNumber, String testingSiteId, ArrayList<String> notifications) {
        super(user_id, userGivenName, userFamilyName, userName, userPhoneNumber);
        this.testingSiteId = testingSiteId;
        this.notifications = notifications;
    }

    /**
     * Getter for testing site's ID
     * @return receptionist's testing site ID
     */
    public String getTestingSiteId() {
        return testingSiteId;
    }

    /**
     * Getter for receptionist's notifications
     * @return list of notifications for receptionist
     */
    public ArrayList<String> getNotifications() {
        return notifications;
    }

    /**
     * Pushes a new notification into receptionist's list of notifications
     * @param notification a notification to be added to a receptionist's list of notifications
     */
    public void addNotifications(String notification) {
        this.notifications.add(notification);
    }

    /**
     * Updates all receptionists who works at a testing site in the list of testing site IDs with the new notification
     * @param notification a notification to be added to receptionist's list of notifications
     * @param testingSiteIds a list of testingSiteIds to identify which receptionists' notifications are to be updated
     * @throws Exception for Error in request
     */
    @Override
    public void update(String notification, ArrayList<String> testingSiteIds) throws Exception {

        if (testingSiteIds.contains(this.getTestingSiteId())){
            this.addNotifications(notification);

            String jsonString = "{ \"additionalInfo\":{" +
                    "\"testingSiteId\":\"" + this.getTestingSiteId() + "\"";

            jsonString += ",\"notifications\": [";

            for (int i = 0; i < this.getNotifications().size() - 1; i++) {
                jsonString += "\"" + this.getNotifications().get(i) + "\",";
            }
            jsonString += "\"" + this.getNotifications().get(this.getNotifications().size()-1) + "\"";

            jsonString += "] }" + "}";

            String url = "https://fit3077.com/api/v2/user";
            new HttpHelper().patchService(url, jsonString, this.getId());
        }
    }

    /**
     * Constructor of User without additional info
     * @return receptionist's attributes in JSON formatted String
     */
    @Override
    public String toString() {
        return "User{\n" +
                "id='" + this.getId() + '\'' +
                ", \ngivenName='" + this.getGivenName() + '\'' +
                ", \nfamilyName='" + this.getFamilyName() + '\'' +
                ", \nuserName='" + this.getUserName() + '\'' +
                ", \nphoneNumber='" + this.getPhoneNumber() + '\'' +
                ", \ntestingSiteId='" + this.getTestingSiteId() + '\'' +
                ", \nnotifications=" + this.getNotifications() +
                "\n}";
    }
}
