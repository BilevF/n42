package com.bilev.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true, of = {})
public class ContractDto extends BasicContractDto {

    private Set<BasicOptionDto> options = new HashSet<>();

    private Set<BasicOptionDto> basket = new HashSet<>();

    private Set<HistoryDto> histories = new HashSet<>();
}
