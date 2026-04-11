package com.example.session2.component;

import org.springframework.stereotype.Component;

@Component
public class NotificationComponent {

    public String sendNotification() {
        return "Notification sent";
    }
}