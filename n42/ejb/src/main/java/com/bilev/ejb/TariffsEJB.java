package com.bilev.ejb;

import com.bilev.dto.BasicTariffDto;
import com.bilev.qualifier.OnMessage;
import com.bilev.qualifier.OnReload;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import java.util.HashSet;

import java.util.Set;


@Named
@Singleton
public class TariffsEJB implements java.io.Serializable {

    private Client client = ClientBuilder.newClient();

    @Inject
    @OnReload
    private Event<Boolean> reload;


    @Getter
    private Set<BasicTariffDto> tariffs = new HashSet<>();

    @PostConstruct
    public void init () {
        try {
            tariffs = client.target("http://localhost:8080/n42/rest/tariff/list").
                    request(MediaType.APPLICATION_JSON_TYPE).
                    get(new GenericType<Set<BasicTariffDto>>() {
                    });
        } catch (Exception ignored) { }
    }

    public void reLoad(@Observes @OnMessage Boolean message) {
        try {
            tariffs = client.target("http://localhost:8080/n42/rest/tariff/list").
                    request(MediaType.APPLICATION_JSON_TYPE).
                    get(new GenericType<Set<BasicTariffDto>>() {
                    });
            reload.fire(true);
            System.out.println("reLoad!!!!!!!!!!!!!!!!");

        } catch (Exception ignored) { }

    }

}
