package com.bilev.ejb;

import com.bilev.dto.BasicTariffDto;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import java.util.HashSet;

import java.util.Set;

@Singleton
public class TariffsEJB {

    private Client client = ClientBuilder.newClient();

    @Getter
    private Set<BasicTariffDto> tariffs = new HashSet<>();

    @PostConstruct
    public void init () {
        reLoad();
    }

    public void reLoad() {
        tariffs = client.target("http://localhost:8080/SpringHibernateExample/rest").
                request(MediaType.APPLICATION_JSON_TYPE).
                get(new GenericType<Set<BasicTariffDto>>() {
                });
    }

}
