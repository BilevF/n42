package com.bilev.dto;

import com.bilev.model.Block;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true, of = {})
public class BasicContractDto extends AbstractDto  {
    @NotNull(message = "{NotNull.contract.phoneNumber=Phone number can not be blank.}")
    @Size(min=3, max=45, message = "{Size.contract.phoneNumber}")
//    @Digits(integer=45,fraction=0)
    @Pattern(regexp = "[\\d][(][\\d][\\d][\\d][)][\\d][\\d][\\d][-][\\d][\\d][-][\\d][\\d]" , message = "{Pattern.contract.phoneNumber}")
    private String phoneNumber;

    private Block.BlockType blockType;

    @NotNull
    private Integer userId;

    private BasicTariffDto tariff = new BasicTariffDto();

    private Double userBalance;

    private Set<BasicOptionDto> options = new HashSet<>();
}
