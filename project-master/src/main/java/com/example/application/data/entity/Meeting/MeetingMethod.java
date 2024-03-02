package com.example.application.data.entity.Meeting;

/**
 * This is a part of strategy pattern applied
 * Meeting link can be generated based on implementation of different child class implementing this interface
 */
public interface MeetingMethod {
    // Generates a unique url with the id
    String generateUrl(String id);
}
