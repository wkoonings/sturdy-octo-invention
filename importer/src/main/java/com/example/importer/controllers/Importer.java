package com.example.importer.controllers;

import com.example.importer.models.CustomMessage;
import com.example.importer.messaging.MessagingSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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
        // checking if message is sent in the last 10 seconds, otherwise we assume a timeout
        if (Duration.between(message.getTimestamp(), LocalDateTime.now()).getSeconds() < 10) {
            // adding 10 seconds to time when message was sent
            LocalDateTime tenSecondsAfterSend = message.getTimestamp().plusSeconds(10);
            // casting LocalDateTime to Date because LocalDateTime is not accepted in timer().schedule()
            Date tenSecondsAfterSendDate = Date.from(tenSecondsAfterSend.atZone(ZoneId.systemDefault()).toInstant());

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    messagingSender.sendMessage(message);
                }
            }, tenSecondsAfterSendDate);
        }
    }
}
