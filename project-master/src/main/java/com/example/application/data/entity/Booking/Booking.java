package com.example.application.data.entity.Booking;

import com.example.application.data.entity.User.User;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.*;

/**
 * This is the Booking abstract class act as the base for all bookings
 */
public abstract class Booking {
    private String bookingId;
    private User customer;
    private String startTime;
    private String notes;
    private String status;
    private String smsPin;
    private String qrcode;
    private List<BookingMemento> history;
    private String lastUpdateTime;

    /**
     * Constructor of Booking
     * @param startTime booking appointment time
     * @param customer customer for the booking
     * @param notes notes provided for the booking
     */
    public Booking(String startTime, User customer, String notes) {
        this.startTime = startTime;
        this.customer = customer;
        this.notes = notes;
        this.bookingId = null;
        this.status = null;
        this.smsPin = null;
        qrcode = UUID.randomUUID().toString();
        this.history = Arrays.asList(new BookingMemento[3]);
        this.lastUpdateTime = null;
    }

    /**
     * Constructor of Booking
     * @param bookingId booking ID
     * @param startTime booking appointment time
     * @param customer customer for the booking
     * @param notes  notes provided for the booking
     * @param status status of the booking
     * @param smsPin PIN code to verify the booking
     * @param qrcode QR code to verify the booking
     */
    public Booking(String bookingId, String startTime, User customer, String notes, String status, String smsPin, String qrcode, List<BookingMemento> history) {
        this.bookingId = bookingId;
        this.startTime = startTime;
        this.customer = customer;
        this.notes = notes;
        this.status = status;
        this.smsPin = smsPin;
        this.qrcode = qrcode;
        this.history = Objects.requireNonNullElseGet(history, () -> Arrays.asList(new BookingMemento[3]));
        this.lastUpdateTime = null;
    }

    /**
     * Constructor of Booking
     * @param bookingId booking ID
     * @param startTime booking appointment time
     * @param customer customer for the booking
     * @param notes  notes provided for the booking
     * @param status status of the booking
     * @param smsPin PIN code to verify the booking
     * @param qrcode QR code to verify the booking
     */
    public Booking(String bookingId, String startTime, User customer, String notes, String status, String smsPin, String qrcode, List<BookingMemento> history, String lastUpdateTime) {
        this.bookingId = bookingId;
        this.startTime = startTime;
        this.customer = customer;
        this.notes = notes;
        this.status = status;
        this.smsPin = smsPin;
        this.qrcode = qrcode;
        this.history = Objects.requireNonNullElseGet(history, () -> Arrays.asList(new BookingMemento[3]));
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * Getter for QR code
     * @return qrcode
     */
    public String getQrcode() {
        return qrcode;
    }

    /**
     * Getter for booking ID
     * @return booking ID
     */
    public String getBookingId() {
        return bookingId;
    }

    /**
     * Getter for Customer
     * @return Customer
     */
    public User getCustomer() {
        return customer;
    }

    /**
     * Getter for startTime
     * @return booking appointment time
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Getter for notes
     * @return notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Getter for status
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Getter for PIN code
     * @return PIN code
     */
    public String getSmsPin() {
        return smsPin;
    }

    /**
     * Getter for Additional Info of booking in JSON String format
     * @return QR code
     */
    public String getAdditionalInfo() {
        return qrcode;
    }

    /**
     * Getter for history
     * @return List of string contain history of booking
     */
    public List<BookingMemento> getHistory() {
        return history;
    }

    /**
     * Getter for time booking last updated
     * @return string in time format
     */
    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * Setter for booking start time
     * @param startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Memento Design Pattern
     * @return
     */
    public abstract BookingMemento getMemento();

    /**
     * Memento Design Pattern
     * @return
     */
    public abstract void setMemento(BookingMemento memento);

    /**
     * toString method
     * @return String form of the class
     */
    @Override
    public String toString() {
        return "bookingId='" + bookingId + '\'' +
                ", \nuser= " + customer.toString() +
                ", \nstartTime= '" + startTime + '\'' +
                ", \nnotes= '" + notes + '\'' +
                ", \nstatus= '" + status + '\'' +
                ", \nsmsPin= '" + smsPin + '\'';
    }

    /**
     * Memento Design Pattern
     * @return
     */
    protected static class BookingMementoInternal implements BookingMemento {
        private String testingSiteId;
        private String testingSiteName;
        private String startTime;

        BookingMementoInternal(String testingSiteId,String testingSiteName,String startTime){
            setTestingSiteId(testingSiteId);
            setTestingSiteName(testingSiteName);
            setStartTime(startTime);
        }

        BookingMementoInternal(){
            setTestingSiteId(null);
            setTestingSiteName(null);
            setStartTime(null);
        }

        public String getTestingSiteId() {
            return testingSiteId;
        }

        public void setTestingSiteId(String testingSiteId) {
            this.testingSiteId = testingSiteId;
        }

        public String getTestingSiteName() {
            return testingSiteName;
        }

        public void setTestingSiteName(String testingSiteName) {
            this.testingSiteName = testingSiteName;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        @Override
        public String toString() {
            String jsonString = "{";
            if (testingSiteId == null || testingSiteId.equals("null"))
                jsonString += "\"testingsiteid\":" + null ;
            else
                jsonString += "\"testingsiteid\":\"" + testingSiteId +"\"" ;

            if (testingSiteId == null || testingSiteName.equals("null"))
                jsonString += ",\"testingsitename\":" + null ;
            else
                jsonString += ",\"testingsitename\":\"" + testingSiteName +"\"" ;

            jsonString += ",\"starttime\":\"" + startTime +"\"}";
            return jsonString;
        }
    }
}




