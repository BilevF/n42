package com.bilev.bean;

import com.bilev.dto.BasicTariffDto;
import com.bilev.ejb.TariffsEJB;
import org.primefaces.context.RequestContext;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;

import javax.ejb.MessageDriven;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;

import java.util.Collection;

@MessageDriven(activationConfig = {

        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),

        @ActivationConfigProperty(propertyName = "destination", propertyValue = "activemq/queue/TestQueue") })
@ManagedBean(name="HelloJSFBean")
@ViewScoped
public class HelloJSFBean implements java.io.Serializable, MessageListener {

    @Inject
    private TariffsEJB helloEJB;


    private static boolean stop = false;
    private static boolean hasData = false;

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        HelloJSFBean.hasData = hasData;
    }

    public boolean isStop() {
        return stop;
    }

    public void listener() {
        stop = true;
        setHasData(false);
    }

    public Collection<BasicTariffDto> getTariffs() {
        return helloEJB.getTariffs();
    }

    @Override
    public void onMessage(Message message) {
        helloEJB.reLoad();
        stop = false;
        setHasData(true);
    }

}