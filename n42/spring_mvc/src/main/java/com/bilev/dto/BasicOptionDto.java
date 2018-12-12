package com.bilev.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true, of = {})
public class BasicOptionDto extends AbstractDto {

    @NotNull(message = "{NotNull.option.name}")
    @Size(min=2, max=45, message = "{Size.option.name}")
    private String name;

    @NotNull(message = "{NotNull.option.price}")
    @Min(value=0)
    private Double price;

    @NotNull(message = "{NotNull.option.connectionPrice}")
    @Min(value=0)
    private Double connectionPrice;

    @NotNull(message = "{NotNull.option.info}")
    @Size(min=2, max=255, message = "{Size.option.info}")
    private String info;

    @NotNull
    private Integer tariffId;

    private SelectedOptionType selectedOptionType;

    @NotNull
    private List<BasicOptionDto> relatedOptions = new ArrayList<>();

    private boolean availableForRemove;

    public enum SelectedOptionType {
        NON, INCOMPATIBLE, REQUIRED
    }
}
