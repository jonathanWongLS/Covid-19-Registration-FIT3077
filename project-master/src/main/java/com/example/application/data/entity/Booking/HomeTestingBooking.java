package com.example.application.data.entity.Booking;

import com.example.application.data.entity.Meeting.Zoom;
import com.example.application.data.entity.User.User;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

/**
 * This is the children HomeTestingBooking class extending Booking abstract class
 */
public class HomeTestingBooking extends Booking {
    private String url;

    /**
     * Constructor for HomeTestingBooking
     * @param startTime booking appointment time
     * @param user customer for the booking
     * @param notes notes provided for the booking
     */
    public HomeTestingBooking(String startTime, User user, String notes) {
        super(startTime, user, notes);
        generateUrl();
    }

    /**
     * Constructor for HomeTestingBooking
     * @param bookingId booking ID
     * @param startTime booking appointment time
     * @param user customer for the booking
     * @param notes  notes provided for the booking
     * @param status status of the booking
     * @param smsPin PIN code to verify the booking
     * @param qrcode QR code to verify the booking
     * @param url url for the meeting with experts
     */
    public HomeTestingBooking(String bookingId, String startTime, User user, String notes, String status, String smsPin, String qrcode, String url, List<BookingMemento> history) {
        super(bookingId, startTime, user, notes, status, smsPin, qrcode, history);
        this.url = url;
    }

    /**
     * Constructor for HomeTestingBooking
     * @param bookingId booking ID
     * @param startTime booking appointment time
     * @param user customer for the booking
     * @param notes  notes provided for the booking
     * @param status status of the booking
     * @param smsPin PIN code to verify the booking
     * @param qrcode QR code to verify the booking
     * @param url url for the meeting with experts
     */
    public HomeTestingBooking(String bookingId, String startTime, User user, String notes, String status, String smsPin, String qrcode, String url, List<BookingMemento> history, String lastUpdateTime) {
        super(bookingId, startTime, user, notes, status, smsPin, qrcode, history, lastUpdateTime);
        this.url = url;
    }

    /**
     * Method to generate a URL for meeting
     */
    private void generateUrl(){
        url = new Zoom().generateUrl(super.getCustomer().getId()+"-"+super.getStartTime());
    }

    /**
     * Getter for Additional Info of booking in JSON String format
     * @return QR code
     */
    public String getAdditionalInfo(){
        return "{"+
                "\n\"qrcode\":\"" + super.getQrcode() + "\"," +
                "\n\"url\":\"" + this.getUrl() + "\"" +
                "}";
    }

    /**
     * Getter of the url
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Memento Design Pattern
     * @return
     */
    public BookingMemento getMemento(){
        BookingMementoInternal state = new BookingMementoInternal();
        state.setStartTime(super.getStartTime());
        state.setTestingSiteId(null);
        state.setTestingSiteName(null);
        return state;
    }

    /**
     * Memento Design Pattern
     * @return
     */
    public void setMemento(BookingMemento memento){
        BookingMementoInternal state = (BookingMementoInternal) memento;
        super.setStartTime(state.getStartTime());
    }

    /**
     * toString method
     * @return String form of the class
     */
    @Override
    public String toString() {
        return "HomeTestingBooking{\n" +
                super.toString() +
                ", \nqrcode='" + super.getQrcode() + '\'' +
                ", \nurl='" + url + '\'' +
                "\n}";
    }
}
