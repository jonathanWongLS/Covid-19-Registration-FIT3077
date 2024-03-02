package com.example.application.data.entity.BookingMethod;

import com.example.application.data.entity.TestingSite.TestingSite;
import com.example.application.data.entity.User.User;
import java.net.http.HttpResponse;

/**
 * This is the BookingMethod interface providing methods of adding Booking
 */
public interface BookingMethod {
    /**
     * addBooking with Testing site
     * @param site site of booking
     * @param startTime booking appointment time
     * @param user customer for the booking
     * @param notes notes provided for the booking
     * @return Http response from server
     * @throws Exception for Error in request
     */
    HttpResponse<String> registerBooking(TestingSite site, String startTime, User user, String notes) throws Exception;
    /**
     * addBooking without Testing site
     * @param startTime booking appointment time
     * @param user customer for the booking
     * @param notes notes provided for the booking
     * @return Http response from server
     * @throws Exception for Error in request
     */
    HttpResponse<String> registerBooking(String startTime, User user, String notes) throws Exception;
}
