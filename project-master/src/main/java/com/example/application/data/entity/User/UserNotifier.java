package com.example.application.data.entity.User;

import java.util.ArrayList;

/**
 * An observable and when notification gets updated, the list of notifications of User's in users list changes by calling updateUsers().
 */
public class UserNotifier {
    /**
     * A notification to be added to user's list of notifications
     */
    private String notification;

    /**
     * A list of user observers
     */
    private ArrayList<User> users = new ArrayList<>();

    /**
     * A collection of users
     */
    private UserCollection userCollection = new UserCollection();

    /**
     * Constructor (populate list users with users in userCollection)
     */
    public UserNotifier() {
        for (User user: userCollection.getCollection()){
            this.addUserObserver(user.getId());
        }
    }

    /**
     * Add user with userId into list of user observers
     * @param userId user's ID
     */
    public void addUserObserver (String userId) {
        for (User user: userCollection.getCollection()){
            if (user.getId().equals(userId)) {
                users.add(user);
                break;
            }
        }
    }

    /**
     * Removes user with userId from the list of user observers
     * @param userId user's ID
     */
    public void removeUserObserver (String userId) {
        for (User user: users){
            if (user.getId().equals(userId)){
                users.remove(user);
                break;
            }
        }
    }

    /**
     * Calls update() function of all user observers in list called users
     * @param notification a notification to be added to user's list of notifications
     * @param testingSiteIds a list of testingSiteIds to identify which users' notifications are to be updated
     * @throws Exception for Error in request
     */
    public void updateUsers (String notification, ArrayList<String> testingSiteIds) throws Exception {
        this.notification = notification;
        for (User user : this.users ){
            user.update(this.notification, testingSiteIds);
        }
    }
}

