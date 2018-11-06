package com.bilev.dto;

import com.bilev.model.Block;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true, of = {})
public class BasicContractDto extends AbstractDto  {
    private String phoneNumber;

    private Double balance;

    private Block.BlockType blockType;

    private Integer userId;

    private TariffDto tariff = new TariffDto();
}
