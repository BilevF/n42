package com.bilev.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true, of = {})
public class BasicOptionDto extends AbstractDto {

    @NotNull
    @Size(min=2, max=45)
    private String name;

    @NotNull
    private Double price;

    @NotNull
    private Double connectionPrice;

    @NotNull
    @Size(min=2, max=255)
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
