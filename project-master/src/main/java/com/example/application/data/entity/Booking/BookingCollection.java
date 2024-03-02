package com.example.application.data.entity.Booking;

import com.example.application.data.entity.HttpHelper;
import com.example.application.data.entity.TestingSite.TestingSite;
import com.example.application.data.entity.User.Customer;
import com.example.application.data.entity.User.Receptionist;
import com.example.application.data.entity.User.HealthcareWorker;
import com.example.application.data.entity.User.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is the booking collection storing a list of Booking
 */
public class BookingCollection {
    private final List<Booking> collection = new ArrayList<>();

    /**
     * Constructor of BookingCollection
     */
    public BookingCollection(){
        this.refreshCollection();
    }

    /**
     * Refresh the collection data by sending GET to API again
     */
    public void refreshCollection(){
        collection.clear();
        try {
            // Populate list
            getBookingsService();
        }
        catch (Exception exception){
            System.out.println("Booking Collection Populate error " + exception.toString());
        }
    }

    /**
     * Getter for collection of bookings
     * @return collection of bookings
     */
    public List<Booking> getCollection() {
        return collection;
    }

    /**
     * HTTP request to API to populate the list of collection
     * @throws Exception for Error in request
     */
    public void getBookingsService() throws Exception {
        String userUrl = "https://fit3077.com/api/v2/booking";

        HttpResponse<String> response = new HttpHelper().getService(userUrl);
        // The GET /user endpoint returns a JSON array, so we can loop through the response as we could with a normal array/list.
        ObjectNode[] jsonNodes = new ObjectMapper().readValue(response.body(), ObjectNode[].class);
        for (ObjectNode node : jsonNodes) {
            JsonNode userNode = node.get("customer");
            User user = createUser(userNode);
            JsonNode historyNode = node.get("additionalInfo").get("history");
            List<BookingMemento> history = createHistory(historyNode);
            Booking booking;
            if (!node.get("testingSite").asText().equals("null")) {
                JsonNode testingSiteNode = node.get("testingSite");
                TestingSite testingSite = new TestingSite(testingSiteNode.get("id").asText(), testingSiteNode.get("name").asText(), testingSiteNode.get("description").asText(), testingSiteNode.get("websiteUrl").asText(), testingSiteNode.get("phoneNumber").asText(), testingSiteNode.get("address").get("latitude").asDouble(), testingSiteNode.get("address").get("longitude").asDouble(), testingSiteNode.get("address").get("unitNumber").asInt(), testingSiteNode.get("address").get("street").asText(), testingSiteNode.get("address").get("street2").asText(), testingSiteNode.get("address").get("suburb").asText(), testingSiteNode.get("address").get("state").asText(), testingSiteNode.get("address").get("postcode").asText(), testingSiteNode.get("additionalInfo").get("facilityType").asText(), testingSiteNode.get("additionalInfo").get("openTime").asText(), testingSiteNode.get("additionalInfo").get("closeTime").asText(), testingSiteNode.get("additionalInfo").get("waitingTime").asText());
                booking = new OnSiteTestingBooking(node.get("id").asText(), testingSite, node.get("startTime").asText(), user, node.get("notes").asText(), node.get("status").asText(), node.get("smsPin").asText(), node.get("additionalInfo").get("qrcode").asText(), history, node.get("updatedAt").asText());
            } else {
                booking = new HomeTestingBooking(node.get("id").asText(), node.get("startTime").asText(), user, node.get("notes").asText(), node.get("status").asText(), node.get("smsPin").asText(), node.get("additionalInfo").get("qrcode").asText(), node.get("additionalInfo").get("url").asText(), history, node.get("updatedAt").asText());
            }
            collection.add(booking);
        }
    }

    /**
     * Helper method to create user based on their role
     * @param userNode
     * @return User which is Receptionist Customer or Healthcareworker
     */
    private User createUser(JsonNode userNode){
        User user = null;
        if (userNode.get("isCustomer").asBoolean())
            user = new Customer(userNode.get("id").asText(), userNode.get("givenName").asText(), userNode.get("familyName").asText(), userNode.get("userName").asText(), userNode.get("phoneNumber").asText());
        else if (userNode.get("isReceptionist").asBoolean()) {
            ArrayList<String> notifications = new ArrayList<>();
            if (userNode.get("additionalInfo").get("notifications")!=null)
                for (int i = 0; i < userNode.get("additionalInfo").get("notifications").size(); i++) {
                    notifications.add(userNode.get("additionalInfo").get("notifications").get(i).asText());
                }
            user = new Receptionist(userNode.get("id").asText(), userNode.get("givenName").asText(), userNode.get("familyName").asText(), userNode.get("userName").asText(), userNode.get("phoneNumber").asText(), userNode.get("additionalInfo").get("testingSiteId").asText(), notifications);
        }
        else if (userNode.get("isHealthcareWorker").asBoolean())
            user = new HealthcareWorker(userNode.get("id").asText(), userNode.get("givenName").asText(), userNode.get("familyName").asText(), userNode.get("userName").asText(), userNode.get("phoneNumber").asText());
        return user;
    }

    /**
     * Helper method to create history based on the history node
     * @param historyNode
     * @return A list with size of 3 along with history
     */
    public static List<BookingMemento> createHistory(JsonNode historyNode){
        List<BookingMemento> history = Arrays.asList(new BookingMemento[3]);
        if (historyNode==null)
            history = null;
        else if (historyNode.isArray()) {
            if (historyNode.get(0)!=null && !historyNode.get(0).asText().equals("null"))
                history.set(0, new Booking.BookingMementoInternal(historyNode.get(0).get("testingsiteid").asText(),historyNode.get(0).get("testingsitename").asText(),historyNode.get(0).get("starttime").asText()));
            else
                history.set(0, null);
            if (historyNode.get(1)!=null && !historyNode.get(1).asText().equals("null"))
                history.set(1, new Booking.BookingMementoInternal(historyNode.get(1).get("testingsiteid").asText(),historyNode.get(1).get("testingsitename").asText(),historyNode.get(1).get("starttime").asText()));
            else
                history.set(1, null);
            if (historyNode.get(2)!=null && !historyNode.get(2).asText().equals("null"))
                history.set(2, new Booking.BookingMementoInternal(historyNode.get(2).get("testingsiteid").asText(),historyNode.get(2).get("testingsitename").asText(),historyNode.get(2).get("starttime").asText()));
            else
                history.set(2, null);
        }
        return history;
    }

    /**
     * Verify PIN code which will return Booking
     * */
    public Booking verifyPin(String pin) {
        Booking userBookingMethod = null;
        int i = 0;
        boolean endLoop = false;
        while (i<collection.size() && !endLoop){
            if (collection.get(i).getSmsPin().equals(pin)) {
                userBookingMethod = collection.get(i);
                endLoop = true;
            }
            i++;
        }
        return userBookingMethod;
    }

    /**
     * Verify Booking ID which will return Booking
     * */
    public Booking verifyBookingId(String bookingId) {
        Booking userBooking = null;
        int i = 0;
        boolean endLoop = false;
        while (i<collection.size() && !endLoop){
            if (collection.get(i).getBookingId().equals(bookingId)) {
                userBooking = collection.get(i);
                endLoop = true;
            }
            i++;
        }
        return userBooking;
    }

    /**
     * Verify QR code which will return Booking
     * */
    public Booking verifyQr(String qr) {
        Booking userBookingMethod = null;
        int i = 0;
        boolean endLoop = false;
        while (i<collection.size() && !endLoop){
            if (collection.get(i).getQrcode().equals(qr)) {
                userBookingMethod = collection.get(i);
                endLoop = true;
            }
            i++;
        }
        return userBookingMethod;
    }

    /**
     * Search through the booking collection to find the booking match with given booking id
     * @param bookingId
     * @return Corresponding booking if there is one, else null
     */
    public Booking getBookingsByBookingId(String bookingId){
        Booking userBooking = null;
        int i = 0;
        boolean endLoop = false;
        while (i<collection.size() && !endLoop){
            if (collection.get(i).getBookingId().equals(bookingId)) {
                userBooking = collection.get(i);
                endLoop = true;
            }
            i++;
        }
        return userBooking;
    }

    /**
     * Getter for collection of bookings
     * @return collection of bookings
     */
    public List<Booking> getBookingsById(String userId) {
        List<Booking> bookings = new ArrayList<>();
        collection.forEach(booking -> {
            if (booking.getCustomer().getId().equals(userId)){
                bookings.add(booking);
            }
        });
        return bookings;
    }

    /**
     * Verify Booking with Booking ID and PIN which will return User who placed the booking.
     * */
    public Booking verifyBookingIdandPin(String bookingId, String smsPin){
        Booking booking = null;
        int i = 0;
        boolean endLoop = false;
        while (i < collection.size() && !endLoop) {
            if (collection.get(i).getBookingId().equals(bookingId) && collection.get(i).getSmsPin().equals(smsPin)) {
                booking = collection.get(i);
                endLoop = true;
            }
            i++;
        }
        return booking;
    }

    /**
     * Method to search through collection and return current Active or Cancelled Booking
     * @return List of booking
     */
    public List<Booking> getActiveAndCancelledBooking(){
        List<Booking> bookings = new ArrayList<>();
        collection.forEach(booking -> {
            if (!booking.getStatus().equals("COMPLETED")){
                if (!(LocalDateTime.now().compareTo(ZonedDateTime.parse(booking.getStartTime()).toLocalDateTime()) > 0 && booking.getStatus().equals("INITIATED"))){
                    bookings.add(booking);
                }
            }
        });
        return bookings;
    }

    /**
     * Send HTTP request to APU to update the booking
     * @param bookingId
     * @param additionalInfo qrcode, and url if there is one
     * @param history
     * @param previousContent
     * @param newTime booking time
     * @param newSiteId changed site id
     * @return HttpResponse
     * @throws Exception if there is an error sending HTTP request
     */
    public static HttpResponse<String> updateBooking(String bookingId, List<String> additionalInfo, List<BookingMemento> history, BookingMemento previousContent, String newTime, String newSiteId) throws Exception {
        /*
         * additionalInfo[0] = qrcode
         * additionalInfo[1] = url (if exist)
         * */
        updateHistory(history, previousContent);

        String jsonString = "{" +
                "\"startTime\":\"" + newTime + "\"";
        if (newSiteId == null)
            jsonString += ",\"testingSiteId\":" + null;
        else
            jsonString += ",\"testingSiteId\":\"" + newSiteId + "\"";

        jsonString += ",\"additionalInfo\": {" +
                "\"qrcode\": \"" + additionalInfo.get(0) + "\"";
        if (additionalInfo.size() > 1)
            jsonString += ",\"url\": \"" + additionalInfo.get(1) + "\"";

        jsonString += ",\"history\": " + Arrays.toString(history.toArray());
        jsonString += "}" + "}";

        String url = "https://fit3077.com/api/v2/booking";

        return new HttpHelper().patchService(url, jsonString, bookingId);
    }

    /**
     * Update current history list with a new history version
     * SAVE step in memento design pattern
     * @param history  list of history
     * @param previousContent most recent modification history version
     */
    private static void updateHistory(List<BookingMemento> history, BookingMemento previousContent){
        if (history.get(0) == null) {
            history.set(0, previousContent);
        } else if ((history.get(0) != null && history.get(1) == null)) {
            history.set(1, history.get(0));
            history.set(0, previousContent);
        } else {
            history.set(2, history.get(1));
            history.set(1, history.get(0));
            history.set(0, previousContent);
        }
    }

    /**
     * Revert booking to previous version
     * @param bookingId
     * @param additionalInfo qrcode, and url if there is one
     * @param history
     * @param previousContent
     * @param index index of history version that will be revert to
     * @return HttpResponse
     * @throws Exception if there is an error sending HTTP request
     */
    public static HttpResponse<String> revertBooking(String bookingId, List<String> additionalInfo, List<BookingMemento> history, String previousContent, int index) throws Exception {
        /*
         * additionalInfo[0] = qrcode
         * additionalInfo[1] = url (if exist)
         * */
        revertHistory(history, index);
        ObjectNode jsonNodes = new ObjectMapper().readValue(previousContent, ObjectNode.class);

        String jsonString = "{" +
                "\"startTime\":" + jsonNodes.get("starttime");

        if (jsonNodes.get("testingsiteid") != null && !jsonNodes.get("testingsiteid").asText().equals("null"))
            jsonString += ",\"testingSiteId\":\"" + jsonNodes.get("testingsiteid").asText() + "\"";
        else
            jsonString += ",\"testingSiteId\":" + null;

        jsonString += ",\"additionalInfo\": {" +
                "\"qrcode\": \"" + additionalInfo.get(0) + "\"";
        if (additionalInfo.size() > 1)
            jsonString += ",\"url\": \"" + additionalInfo.get(1) + "\"";

        jsonString += ",\"history\": " + history;
        jsonString += "}" + "}";
        String url = "https://fit3077.com/api/v2/booking";

        return new HttpHelper().patchService(url, jsonString, bookingId);
    }

    /**
     * Update current history list to previous version
     * UNDO of memento design pattern
     * @param history list of history
     * @param index index of history that the booking will revert to
     */
    private static void revertHistory(List<BookingMemento> history, int index){
        if (index == 1) {
            history.set(0, history.get(1));
            history.set(1, history.get(2));
            history.set(2, null);
        }
        else if (index == 2) {
            history.set(0, history.get(2));
            history.set(1, null);
            history.set(2, null);
        }
        else {
            history.set(2, null);
            history.set(1, null);
            history.set(0, null);
        }
    }

    /**
     * Send HTTP request to API to cancel a booking
     * @param bookingId
     * @return HTTP Response
     * @throws Exception if there is an error sending HTTP request
     */
    public static HttpResponse<String> cancelBooking(String bookingId) throws Exception {
        String url = "https://fit3077.com/api/v2/booking";
        String jsonString = "{ \"status\":\"CANCELLED\" }";
        return new HttpHelper().patchService(url, jsonString, bookingId);
    }

    /**
     * Send HTTP request to API to indicate a booking has been completed
     * @param bookingId
     * @return HTTP Response
     * @throws Exception if there is an error sending HTTP request
     */
    public static HttpResponse<String> completeBooking(String bookingId) throws Exception {
        String url = "https://fit3077.com/api/v2/booking";
        String jsonString = "{ \"status\":\"COMPLETED\" }";
        return new HttpHelper().patchService(url, jsonString, bookingId);
    }

    /**
     * Send HTTP request to API to delete a booking
     * @param bookingId
     * @return HTTP Response
     * @throws Exception if there is an error sending HTTP request
     */
    public static HttpResponse<String> deleteBooking(String bookingId) throws Exception {
        String url = "https://fit3077.com/api/v2/booking";
        return new HttpHelper().deleteService(url, bookingId);
    }
}
