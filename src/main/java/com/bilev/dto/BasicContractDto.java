package com.bilev.dto;

import com.bilev.model.Block;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true, of = {})
public class BasicContractDto extends AbstractDto  {
    @NotNull
    @Size(min=3, max=45)
    @Digits(integer=45,fraction=0)
    private String phoneNumber;

    private Double balance;

    private Block.BlockType blockType;

    @NotNull
    private Integer userId;

    private TariffDto tariff = new TariffDto();
}
