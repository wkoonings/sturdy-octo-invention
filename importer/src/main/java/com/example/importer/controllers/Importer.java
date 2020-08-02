package com.example.importer.controllers;

import com.example.importer.models.CustomMessage;
import com.example.importer.messaging.MessagingSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class Importer {

    private final MessagingSender messagingSender;

    @Autowired
    public Importer(MessagingSender messagingSender) {
        this.messagingSender = messagingSender;
    }

    @PostMapping
    public void newMessage(@RequestBody CustomMessage message) {
        this.messagingSender.sendMessage(message);
    }
}
