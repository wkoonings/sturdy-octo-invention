package com.example.importer.messaging;

import com.example.importer.models.CustomMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

    @RabbitListener(queues = "importerQueue")
    public void listen(CustomMessage message) {
        System.out.println(message.getMessage());
    }
}
