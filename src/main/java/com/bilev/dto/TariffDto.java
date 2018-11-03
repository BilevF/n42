package com.bilev.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class TariffDto extends AbstractDto {

    private String name;

    private Double price;

    private String info;
}
