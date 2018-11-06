package com.bilev.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true, of = {})
public class BasicOptionDto extends AbstractDto {
    private String name;

    private Double price;

    private Double connectionPrice;

    private String info;

    private Integer tariffId;

    private SelectedOptionType selectedOptionType;

    private List<BasicOptionDto> relatedOptions = new ArrayList<>();

    public enum SelectedOptionType {
        NON, INCOMPATIBLE, REQUIRED
    }
}
