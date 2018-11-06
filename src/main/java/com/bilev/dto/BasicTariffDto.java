package com.bilev.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true, of = {})
public class BasicTariffDto extends AbstractDto {

    private String name;

    private Double price;

    private String info;

    private Boolean valid;
}
