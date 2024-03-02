package com.example.application.data.entity.Booking;

import com.example.application.data.entity.TestingSite.TestingSite;
import com.example.application.data.entity.TestingSite.TestingSiteCollection;
import com.example.application.data.entity.User.User;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

/**
 * This is the children OnSiteTestingBooking class extending Booking abstract class
 */
public class OnSiteTestingBooking extends Booking {
    private TestingSite testingSite;

    /**
     * Constructor for OnSiteTestingBooking
     * @param site site of the booking
     * @param startTime booking appointment time
     * @param user customer for the booking
     * @param notes notes provided for the booking
     */
    public OnSiteTestingBooking(TestingSite site, String startTime, User user, String notes) {
        super(startTime, user, notes);
        this.testingSite = site;
    }

    /**
     * Constructor for OnSiteTestingBooking
     * @param bookingId booking ID
     * @param site site of the booking
     * @param startTime booking appointment time
     * @param user customer for the booking
     * @param notes  notes provided for the booking
     * @param status status of the booking
     * @param smsPin PIN code to verify the booking
     * @param qrcode QR code to verify the booking
     */
    public OnSiteTestingBooking(String bookingId, TestingSite site, String startTime, User user, String notes, String status, String smsPin, String qrcode, List<BookingMemento> history) {
        super(bookingId, startTime, user, notes, status, smsPin, qrcode, history);
        this.testingSite = site;
    }

    /**
     * Constructor for OnSiteTestingBooking
     * @param bookingId booking ID
     * @param site site of the booking
     * @param startTime booking appointment time
     * @param user customer for the booking
     * @param notes  notes provided for the booking
     * @param status status of the booking
     * @param smsPin PIN code to verify the booking
     * @param qrcode QR code to verify the booking
     */
    public OnSiteTestingBooking(String bookingId, TestingSite site, String startTime, User user, String notes, String status, String smsPin, String qrcode, List<BookingMemento> history, String lastUpdateTime) {
        super(bookingId, startTime, user, notes, status, smsPin, qrcode, history, lastUpdateTime);
        this.testingSite = site;
    }

    /**
     * Getter of the testingSite
     * @return testing site
     */
    public TestingSite getTestingSite() {
        return testingSite;
    }

    /**
     * Getter for Additional Info of booking in JSON String format
     * @return QR code
     */
    public String getAdditionalInfo(){
        return "{"+
                "\n\"qrcode\":\"" + super.getQrcode() + "\"" +
                "}";
    }

    /**
     * Memento Design Pattern
     * @return
     */
    public BookingMemento getMemento(){
        BookingMementoInternal state = new BookingMementoInternal();
        state.setStartTime(super.getStartTime());
        state.setTestingSiteId(testingSite.getId());
        state.setTestingSiteName(testingSite.getName());
        return state;
    }

    /**
     * Memento Design Pattern
     * @return
     */
    public void setMemento(BookingMemento memento){
        BookingMementoInternal state = (BookingMementoInternal) memento;
        super.setStartTime(state.getStartTime());
        testingSite.setId(state.getTestingSiteId());
        testingSite.setName(state.getTestingSiteName());
    }

    /**
     * toString method
     * @return String form of the class
     */
    @Override
    public String toString() {
        return "OnSiteTestingBooking {\n" +
                super.toString() +
                ", \nqrcode='" + super.getQrcode() + '\'' +
                ",\ntestingSite=" + testingSite.getInfo() +
                "\n}";
    }
}
