package com.example.application.data.entity.CovidTest;

import com.example.application.data.entity.Booking.Booking;
import com.example.application.data.entity.TestingType.TestingType;
import com.example.application.data.entity.User.User;

/**
 * This is the CovidTest class representing the covid test data from API
 */
public class CovidTest {
    private String id;
    private TestingType testingType;
    private User patient;
    private User administerer;
    private Booking booking;
    private String result;
    private String status;
    private String notes;
    private String additionalInfo;

    /**
     * Constructor for CovidTest
     * @param testingType testing type of covid test
     * @param patient patient of covid test
     * @param booking booking of the covid test
     * @param result result of the covid test
     * @param status status of the covid test
     * @param notes notes of the covid test
     * @param additionalInfo additionalInfo of the covid test
     */
    public CovidTest(TestingType testingType, User patient, Booking booking, String result, String status, String notes, String additionalInfo) {
        this.id = null;
        this.testingType = testingType;
        this.patient = patient;
        this.administerer = null;
        this.booking = booking;
        this.result = result;
        this.status = status;
        this.notes = notes;
        this.additionalInfo = additionalInfo;
    }

    /**
     * Constructor for CovidTest
     * @param testingType testing type of covid test
     * @param patient patient of covid test
     * @param administerer administerer of the covid test
     * @param result result of the covid test
     * @param status status of the covid test
     * @param notes notes of the covid test
     * @param additionalInfo additionalInfo of the covid test
     */
    public CovidTest(TestingType testingType, User patient, User administerer, String result, String status, String notes, String additionalInfo) {
        this.id = null;
        this.testingType = testingType;
        this.patient = patient;
        this.administerer = administerer;
        this.booking = null;
        this.result = result;
        this.status = status;
        this.notes = notes;
        this.additionalInfo = additionalInfo;
    }

    /**
     * Constructor for CovidTest
     * @param testingType testing type of covid test
     * @param patient patient of covid test
     * @param administerer administerer of the covid test
     * @param booking booking of the covid test
     * @param result result of the covid test
     * @param status status of the covid test
     * @param notes notes of the covid test
     * @param additionalInfo additionalInfo of the covid test
     */
    public CovidTest(TestingType testingType, User patient, User administerer, Booking booking, String result, String status, String notes, String additionalInfo) {
        this.id = null;
        this.testingType = testingType;
        this.patient = patient;
        this.administerer = administerer;
        this.booking = booking;
        this.result = result;
        this.status = status;
        this.notes = notes;
        this.additionalInfo = additionalInfo;
    }

    /**
     * Constructor for CovidTest
     * @param id id of the covid test
     * @param testingType testing type of covid test
     * @param patient patient of covid test
     * @param administerer administerer of the covid test
     * @param booking booking of the covid test
     * @param result result of the covid test
     * @param status status of the covid test
     * @param notes notes of the covid test
     * @param additionalInfo additionalInfo of the covid test
     */
    public CovidTest(String id, TestingType testingType, User patient, User administerer, Booking booking, String result, String status, String notes, String additionalInfo) {
        this.id = id;
        this.testingType = testingType;
        this.patient = patient;
        this.administerer = administerer;
        this.booking = booking;
        this.result = result;
        this.status = status;
        this.notes = notes;
        this.additionalInfo = additionalInfo;
    }

    /**
     * Getter
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Getter
     * @return testing type
     */
    public TestingType getTestingType() {
            return testingType;
    }

    /**
     * Getter
     * @return patient
     */
    public User getPatient() {
        return patient;
    }

    /**
     * Getter
     * @return administerer
     */
    public User getAdministerer() {
        return administerer;
    }

    /**
     * Getter
     * @return booking
     */
    public Booking getBooking() {
        return booking;
    }

    /**
     * Getter
     * @return result
     */
    public String getResult() {
        return result;
    }

    /**
     * Getter
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Getter
     * @return notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Getter
     * @return additional info
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * toString method
     * @return String form of the class
     */
    @Override
    public String toString() {
        return "CovidTest{" +
                "id='" + id + '\'' +
                "\n, testingType=" + testingType.getType() +
                "\n, patient=" + patient.toString() +
                "\n, administerer=" + administerer.toString() +
                "\n, booking=" + booking.toString() +
                "\n, result='" + result + '\'' +
                "\n, status='" + status + '\'' +
                "\n, notes='" + notes + '\'' +
                "\n, additionalInfo='" + additionalInfo + '\'' +
                '}';
    }
}
