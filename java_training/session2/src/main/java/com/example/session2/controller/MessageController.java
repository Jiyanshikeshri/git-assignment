package com.example.session2.controller;

import com.example.session2.service.MessageService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    // Constructor Injection
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public String getMessage(@RequestParam String type) {
        return messageService.getMessage(type);
    }
}