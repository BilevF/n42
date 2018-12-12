package com.bilev.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true, of = {})
public class BasicTariffDto extends AbstractDto {

    @NotNull(message = "{NotNull.tariff.name}")
    @Size(min=2, max=45, message = "{Size.tariff.name}")
    private String name;

    @NotNull(message = "{NotNull.tariff.price}")
    @Min(value=0)
    private Double price;

    @NotNull(message = "{NotNull.tariff.info}")
    @Size(min=2, max=255, message = "{Size.tariff.info}")
    private String info;

    @NotNull(message = "{NotNull.tariff.valid}")
    private Boolean valid;
}
