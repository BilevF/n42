package com.bilev.service.impl;

import com.bilev.service.api.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;


@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    JmsTemplate jmsTemplate;

    @Override
    public void notifyTariffChanged() {
        try {
            jmsTemplate.send(session -> session.createObjectMessage(true));
        } catch (Exception ignored) { }
    }
}
