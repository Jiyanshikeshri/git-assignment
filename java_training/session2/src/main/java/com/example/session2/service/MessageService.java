package com.example.session2.service;

import com.example.session2.component.ShortMessageFormatter;
import com.example.session2.component.LongMessageFormatter;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final ShortMessageFormatter shortFormatter;
    private final LongMessageFormatter longFormatter;

    // Constructor Injection
    public MessageService(ShortMessageFormatter shortFormatter,
                          LongMessageFormatter longFormatter) {
        this.shortFormatter = shortFormatter;
        this.longFormatter = longFormatter;
    }

    public String getMessage(String type) {

        if(type.equalsIgnoreCase("SHORT")) {
            return shortFormatter.formatMessage();
        } else {
            return longFormatter.formatMessage();
        }
    }
}