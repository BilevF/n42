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

    @NotNull
    @Size(min=2, max=45)
    private String name;

    @NotNull
    @Min(value=0)
    private Double price;

    @NotNull
    @Size(min=2, max=255)
    private String info;

    @NotNull
    private Boolean valid;
}
