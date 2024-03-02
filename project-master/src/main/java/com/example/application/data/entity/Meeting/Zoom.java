package com.example.application.data.entity.Meeting;

public class Zoom implements MeetingMethod{

    // Generates a URL for a Zoom meeting with id
    @Override
    public String generateUrl(String id) {
        return "https://zoom.us/"+id;
    }
}
