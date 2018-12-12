package com.bilev.service.impl;

import com.bilev.messaging.MessageSender;
import com.bilev.service.api.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageSender messageSender;

    @Override
    public void notifyTariffChanged() {
        try {
            messageSender.sendMessage();
        } catch (Exception ignored) { }
    }
}
