package com.bilev.dto;

import com.bilev.model.Block;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true, of = {})
public class ContractDto extends BasicContractDto {

    private Set<BasicOptionDto> basket = new HashSet<>();

    private Set<HistoryDto> histories = new HashSet<>();
}
