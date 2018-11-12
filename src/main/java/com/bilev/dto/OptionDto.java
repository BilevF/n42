package com.bilev.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true, of = {})
public class OptionDto extends BasicOptionDto {

    private Set<BasicOptionDto> requiredOptions = new HashSet<>();

    private Set<BasicOptionDto> incompatibleOptions = new HashSet<>();

    private Set<BasicOptionDto> incompatibleOptionsOf = new HashSet<>();
}
