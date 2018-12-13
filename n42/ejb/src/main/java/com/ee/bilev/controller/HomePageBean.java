package com.ee.bilev.controller;

import com.ee.bilev.dto.BasicTariffDto;
import com.ee.bilev.service.TariffsEJB;

import com.ee.bilev.qualifier.OnReload;

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
    private TariffsEJB tariffEJB;

    @Inject
    @Push(channel = "tariffChannel")
    private PushContext tariffChannel;


    public Collection<BasicTariffDto> getTariffs() {
        return tariffEJB.getTariffs();

    }

    public void observeEvent(@Observes @OnReload Boolean message){
        System.out.println("observeEvent");
        tariffChannel.send(true);
    }

}