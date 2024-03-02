package com.example.application.data.entity.BookingMethod;

import com.example.application.data.entity.Booking.Booking;
import com.example.application.data.entity.Booking.OnSiteTestingBooking;
import com.example.application.data.entity.Booking.HomeTestingBooking;
import com.example.application.data.entity.HttpHelper;
import com.example.application.data.entity.TestingSite.TestingSite;
import com.example.application.data.entity.User.User;
import java.net.http.HttpResponse;

/**
 * addBooking with TestingSite is for onSiteTesting, return null on default
 * addBooking without TestingSite if for onlineBooking
 * */
public class SystemBookingMethod implements BookingMethod {
    /**
     * addBooking with Testing site
     * @param site site of booking
     * @param startTime booking appointment time
     * @param user customer for the booking
     * @param notes notes provided for the booking
     * @return Http response from server
     * @throws Exception for Error in request
     */
    @Override
    public HttpResponse<String> registerBooking(TestingSite site, String startTime, User user, String notes) throws Exception {
        Booking booking = new OnSiteTestingBooking(site,startTime,user,notes);
        String jsonString = "{" +
                "\"customerId\":\"" + user.getId() + "\"," +
                "\"testingSiteId\":\"" + site.getId() + "\"," +
                "\"startTime\":\"" + startTime + "\"";
        if (notes != null && !notes.isBlank())
            jsonString += ",\"notes\":\"" + notes + "\"";
        jsonString += ",\"additionalInfo\": " + booking.getAdditionalInfo();
        // update Site waiting time
        jsonString += "}";
        String testingSiteUrl = "https://fit3077.com/api/v2/testing-site";
        String testingSiteJson = "{" +
                "\"additionalInfo\": {"+
                "\"facilityType\": \"" + site.getFacilityType() + "\""+
                ",\"openTime\": \"" + site.getOpenTime() + "\""+
                ",\"closeTime\": \"" + site.getCloseTime() + "\""+
                ",\"waitingTime\": \"" + (Integer.parseInt(site.getWaitingTime().substring(0,site.getWaitingTime().length()-3))+10)+ "min\""+
                "}"+
                "}";
        HttpResponse<String> response = new HttpHelper().patchService(testingSiteUrl,testingSiteJson, site.getId());
        String url = "https://fit3077.com/api/v2/booking";
        return new HttpHelper().postService(url,jsonString);
    }

    /**
     * addBooking without Testing site
     * @param startTime booking appointment time
     * @param user customer for the booking
     * @param notes notes provided for the booking
     * @return Http response from server
     * @throws Exception for Error in request
     */
    @Override
    public HttpResponse<String> registerBooking(String startTime, User user, String notes) throws Exception {
        Booking booking = new HomeTestingBooking(startTime,user,notes);
        String jsonString = "{" +
                "\"customerId\":\"" + booking.getCustomer().getId() + "\"," +
                "\"startTime\":\"" + booking.getStartTime() + "\"";
        if (notes != null && !notes.isBlank())
            jsonString += ",\"notes\":\"" + notes + "\"";
        jsonString += ",\"additionalInfo\": " + booking.getAdditionalInfo();
        jsonString += "}";
        String url = "https://fit3077.com/api/v2/booking";
        return new HttpHelper().postService(url,jsonString);
    }
}
