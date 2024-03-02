package com.example.application.data.entity.User;

import com.example.application.data.entity.HttpHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the user collection storing a list of User
 */
public class UserCollection {

    /**
     * List of all Users from API
     */
    private List<User> collection = new ArrayList<>();

    /**
     * Constructor of UserCollection
     */
    public UserCollection(){
        try {
            getUsersService();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * Getter for collection of users
     * @return collection of users
     */
    public List<User> getCollection() {
        return collection;
    }

    /**
     * Verifies if User is in the collection using username and password
     * @param username user's username
     * @param password user's password
     * @return user if user is in the collection, null otherwise
     */
    public User verifyUserId(String username, String password){
        User user = null;
        if (verifyUserService(username,password)) {
            int i = 0;
            boolean endLoop = false;
            while (i<collection.size() && !endLoop){
                if (collection.get(i).getUserName().equals(username)) {
                    user = collection.get(i);
                    endLoop = true;
                }
                i++;
            }
        }
        return user;
    }

    /**
     * HTTP request to API to populate the list of users
     * @throws Exception for Error in request
     */
    public void getUsersService() throws Exception{
        String userUrl = "https://fit3077.com/api/v2/user";

        HttpResponse<String> response = new HttpHelper().getService(userUrl);

        // Error checking for this sample code. You can check the status code of your request, as part of performing error handling in your assignment.
        if (response.statusCode() != 200) {
            throw new Exception("Please specify your API key in line 21 to continue using this sample code.");
        }

        // The GET /user endpoint returns a JSON array, so we can loop through the response as we could with a normal array/list.
        ObjectNode[] jsonNodes = new ObjectMapper().readValue(response.body(), ObjectNode[].class);

        // Creates Users of specific roles based on attributes of the User
        for (ObjectNode node: jsonNodes) {
            User user = null;
            // Create Customer object if User is a Customer
            if(node.get("isCustomer").asBoolean())
                user = new Customer(node.get("id").asText(),node.get("givenName").asText(),node.get("familyName").asText(),node.get("userName").asText(),node.get("phoneNumber").asText());
            // Create FacilityStaff object if User is a Receptionist
            else if (node.get("isReceptionist").asBoolean()) {
                ArrayList<String> notifications = new ArrayList<>();
                for (int i = 0; i < node.get("additionalInfo").get("notifications").size(); i++) {
                    notifications.add(node.get("additionalInfo").get("notifications").get(i).asText());
                }
                user = new Receptionist(node.get("id").asText(), node.get("givenName").asText(), node.get("familyName").asText(), node.get("userName").asText(), node.get("phoneNumber").asText(), node.get("additionalInfo").get("testingSiteId").asText(), notifications);
            }
            // Create HealthcareWorker object if User is a HealthcareWorker
            else if (node.get("isHealthcareWorker").asBoolean())
                user = new HealthcareWorker(node.get("id").asText(),node.get("givenName").asText(),node.get("familyName").asText(),node.get("userName").asText(),node.get("phoneNumber").asText());
            if(user!=null)
                collection.add(user);
        }
    }

    /**
     * Getter for list of only Receptionist type
     * @return list of only Receptionist type
     */
    public ArrayList<Receptionist> getReceptionists(){
        ArrayList<Receptionist> receptionists = new ArrayList<>();

        for (User user: collection) {
            if (user instanceof Receptionist) {
                receptionists.add((Receptionist) user);
            }
        }
        return receptionists;
    }

    /**
     * Get notifications for receptionist by their ID
     * @param receptionistId receptionist's ID
     * @return the notification of receptionist with ID provided
     */
    public ArrayList<String> getNotificationsByReceptionistId (String receptionistId){
        ArrayList<Receptionist> receptionistList = this.getReceptionists();

        for (Receptionist rec: receptionistList){
            if (rec.getId().equals(receptionistId)){
                return rec.getNotifications();
            }
        }
        return null;
    }

    /**
     * Static method to clear notifications of receptionists who work at a testing side specified by the ID
     * @param userId receptionist's ID
     * @param testingSiteId ID of testing site the receptionist works at
     * @return response from API
     * @throws Exception for Error in request
     */
    public static HttpResponse<String> clearNotifications(String userId, String testingSiteId) throws Exception {
        String jsonString = "{ \"additionalInfo\": {" +
                "\"testingSiteId\":\"" + testingSiteId+ "\"";
        jsonString += ",\"notifications\": []";
        jsonString += "}" + "}";

        String url = "https://fit3077.com/api/v2/user";

        return new HttpHelper().patchService(url, jsonString, userId);
    }

    /**
     * Verifies is User exists in collection of Users using username and password
     * @param username user's username
     * @param password user's password
     * @return boolean true if user exists, false otherwise
     */
    public boolean verifyUserService(String username, String password){
        boolean userIsValid;
        String jsonString = "{"+
                "\"userName\":\"" + username + "\"," +
                "\"password\":\"" + password + "\""+
                "}";

        String url = "https://fit3077.com/api/v2/user/login?jwt=false";

        try {
            HttpResponse<String> response = new HttpHelper().postService(url,jsonString);
            userIsValid = response.statusCode() == 200;
        }
        catch (Exception e){
            System.out.println(e);
            userIsValid = false;
        }
        return userIsValid;
    }

    /**
     * POSTS and stores a User to the API  (with additional info)
     * @param givenName user's given name
     * @param familyName user's family name
     * @param userName user's username
     * @param password user's password
     * @param phoneNumber user's phone number
     * @param isCustomer true if user is a customer, false otherwise
     * @param isAdmin true if user is a receptionist, false otherwise
     * @param isHealthCareWorker true if user is a healthcare worker, false otherwise
     * @param additionalInfo user's additional info
     * @return newly added User
     * @throws Exception for Error in request
     */
    public User addUserService(String givenName,String familyName, String userName, String password, String phoneNumber, boolean isCustomer, boolean isAdmin, boolean isHealthCareWorker, String additionalInfo) throws Exception{
        String url = "https://fit3077.com/api/v2/user";
        String jsonString = "{" +
                "\"givenName\":\"" + givenName + "\"," +
                "\"familyName\":\"" + familyName + "\"," +
                "\"userName\":\"" + userName + "\"," +
                "\"password\":\"" + password + "\"," +
                "\"phoneNumber\":\"" + phoneNumber + "\"," +
                "\"isCustomer\":" + isCustomer + "," +
                "\"isAdmin\":" + isAdmin + "," +
                "\"isHealthCareWorker\":" + isHealthCareWorker + "," +
                "\"additionalInfo\":" + additionalInfo +
                "}";
        HttpResponse<String> response = new HttpHelper().postService(url,jsonString);
        ObjectNode mappedResponse = new ObjectMapper().readValue(response.body(),ObjectNode.class);
        return createUser(mappedResponse);
    }

    /**
     * POSTS and stores a User to the API  (without additional info)
     * @param givenName user's given name
     * @param familyName user's family name
     * @param userName user's username
     * @param password user's password
     * @param phoneNumber user's phone number
     * @param isCustomer true if user is a customer, false otherwise
     * @param isAdmin true if user is a receptionist, false otherwise
     * @param isHealthCareWorker true if user is a healthcare worker, false otherwise
     * @return newly added User
     * @throws Exception for Error in request
     */
    public User addUserService(String givenName,String familyName, String userName, String password, String phoneNumber, boolean isCustomer, boolean isAdmin, boolean isHealthCareWorker) throws Exception{
        String url = "https://fit3077.com/api/v2/user";
        String jsonString = "{" +
                "\"givenName\":\"" + givenName + "\"," +
                "\"familyName\":\"" + familyName + "\"," +
                "\"userName\":\"" + userName + "\"," +
                "\"password\":\"" + password + "\"," +
                "\"phoneNumber\":\"" + phoneNumber + "\"," +
                "\"isCustomer\":" + isCustomer + "," +
                "\"isAdmin\":" + isAdmin + "," +
                "\"isHealthCareWorker\":" + isHealthCareWorker +
                "}";
        HttpResponse<String> response = new HttpHelper().postService(url,jsonString);
        ObjectNode mappedResponse = new ObjectMapper().readValue(response.body(),ObjectNode.class);
        return createUser(mappedResponse);
    }

    /**
     * Create a User using response from API
     * @param mappedResponse user node retrieved from the API
     * @return user if user can be created, null otherwise
     */
    private User createUser(ObjectNode mappedResponse){
        User user = null;
        if(mappedResponse.get("isCustomer").asBoolean())
            user = new Customer(mappedResponse.get("id").asText(),mappedResponse.get("givenName").asText(),mappedResponse.get("familyName").asText(),mappedResponse.get("userName").asText(),mappedResponse.get("phoneNumber").asText());
        else if (mappedResponse.get("isReceptionist").asBoolean()) {
            ArrayList<String> notifications = new ArrayList<>();
            for (int i = 0; i < mappedResponse.get("additionalInfo").get("notifications").size(); i++) {
                notifications.add(mappedResponse.get("additionalInfo").get("notifications").get(i).asText());
            }
            user = new Receptionist(mappedResponse.get("id").asText(), mappedResponse.get("givenName").asText(), mappedResponse.get("familyName").asText(), mappedResponse.get("userName").asText(), mappedResponse.get("phoneNumber").asText(), mappedResponse.get("additionalInfo").get("testingSiteId").asText(), notifications);
        }
        else if (mappedResponse.get("isHealthcareWorker").asBoolean())
            user = new HealthcareWorker(mappedResponse.get("id").asText(),mappedResponse.get("givenName").asText(),mappedResponse.get("familyName").asText(),mappedResponse.get("userName").asText(),mappedResponse.get("phoneNumber").asText());
        return user;
    }
}
