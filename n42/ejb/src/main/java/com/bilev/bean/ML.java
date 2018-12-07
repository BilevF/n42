package com.bilev.bean;

import com.bilev.ejb.TariffsEJB;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.jboss.ejb3.annotation.ResourceAdapter;

//@MessageDriven(activationConfig = {
//
//        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
//
//        @ActivationConfigProperty(propertyName = "destination", propertyValue = "activemq/queue/TestQueue") })
//public class ML implements MessageListener {
//    @EJB
//    private TariffsEJB helloEJB;
//
//    @Override
//    public void onMessage(Message message) {
//        helloEJB.reLoad();
//    }
//}
