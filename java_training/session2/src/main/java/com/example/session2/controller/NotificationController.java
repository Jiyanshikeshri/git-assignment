package com.example.session2.controller;

import com.example.session2.service.NotificationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notify")
public class NotificationController {

    private final NotificationService notificationService;

    // Constructor Injection
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public String sendNotification() {
        return notificationService.triggerNotification();
    }
}