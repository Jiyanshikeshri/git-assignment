package com.example.session2.component;

import com.example.session2.service.MessageFormatter;
import org.springframework.stereotype.Component;

@Component("shortFormatter")
public class ShortMessageFormatter implements MessageFormatter {

    @Override
    public String formatMessage() {
        return "This is a short message";
    }
}