package com.ee.bilev.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(of = {"id"})
public class BasicTariffDto {

    private Integer id;

    private String name;

    private Double price;

    private String info;

    private Boolean valid;
}
