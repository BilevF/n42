package com.bilev.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class BasicOptionDto extends AbstractDto {
    private String name;

    private Double price;

    private Double connectionPrice;

    private String info;

    private Integer tariffId;

    private SelectedOptionType selectedOptionType;
}
