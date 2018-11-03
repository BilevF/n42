package com.bilev.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class UserDto extends BasicUserDto {

    private List<ContractDto> contracts;
}
