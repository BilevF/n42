package com.bilev.dto;

import com.bilev.model.Block;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class ContractDto extends AbstractDto {

    private String phoneNumber;

    private Double balance;

    private Block.BlockType blockType;

    private Integer userID;

    private TariffDto tariffDto;

    private List<BasicOptionDto> options;
}
