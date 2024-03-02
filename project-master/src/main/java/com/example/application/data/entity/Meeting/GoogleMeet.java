package com.example.application.data.entity.Meeting;

public class GoogleMeet implements MeetingMethod{

    // Generates a URL for a Google Meet meeting with id
    @Override
    public String generateUrl(String id) {
        return "https://googlemeet.com/"+id;
    }
}
