package com.example.importer.messaging;

import com.example.importer.models.CustomMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessagingSender {

    private final RabbitTemplate template;

    public MessagingSender(RabbitTemplate template) {
        this.template = template;
    }

    public void sendMessage(CustomMessage message) {
        template.convertAndSend("importerQueue", message);
    }
}
