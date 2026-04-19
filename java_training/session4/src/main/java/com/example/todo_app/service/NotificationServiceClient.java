package com.example.todo_app.service;

import org.springframework.stereotype.Component;

// This is a dummy service to implement external notification system
@Component
public class NotificationServiceClient {

    // This method is used for sending notification
    public void sendNotification(String message) {

        // In real world, this could be email or sms
        System.out.println("Notification sent: " + message);
    }
}