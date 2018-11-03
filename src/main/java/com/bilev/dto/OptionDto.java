package com.bilev.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class OptionDto extends BasicOptionDto {

    private Set<BasicOptionDto> requiredOptions;

    private Set<BasicOptionDto> incompatibleOptions;
}
