package com.bilev.bean;

import com.bilev.dto.BasicTariffDto;
import com.bilev.ejb.TariffsEJB;

import com.bilev.qualifier.OnReload;

import javax.enterprise.context.ApplicationScoped;

import javax.enterprise.event.Observes;

import javax.faces.push.Push;
import javax.faces.push.PushContext;

import javax.inject.Inject;
import javax.inject.Named;


import java.util.Collection;


@Named
@ApplicationScoped
public class HomePageBean implements java.io.Serializable {

    @Inject
    private TariffsEJB helloEJB;

    @Inject
    @Push(channel = "tariffChannel")
    private PushContext tariffChannel;


    public Collection<BasicTariffDto> getTariffs() {
        return helloEJB.getTariffs();

    }

    public void observeEvent(@Observes @OnReload Boolean message){
        tariffChannel.send(true);
        System.out.println("observeEvent!!!!!!!!!!!!!!!!!11");
    }

}