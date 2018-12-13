package com.ee.bilev.service;

import com.ee.bilev.qualifier.OnMessage;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Message;
import javax.jms.MessageListener;


@MessageDriven(activationConfig = {

        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),

        @ActivationConfigProperty(propertyName = "destination", propertyValue = "activemq/queue/TestQueue") })
@Named
public class MessageEventBean implements MessageListener, java.io.Serializable {

    @Inject
    @OnMessage
    private Event<Boolean> updated;

    @Override
    public void onMessage(Message message) {
        updated.fire(true);
    }

}
