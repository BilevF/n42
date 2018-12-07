package com.bilev.messaging;

import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;


@Component
public class MessageSender {

    @Autowired
    JmsTemplate jmsTemplate;

    public void sendMessage() {

        jmsTemplate.send(session -> {
            ObjectMessage objectMessage = session.createObjectMessage(true);
            return objectMessage;
        });
    }

}