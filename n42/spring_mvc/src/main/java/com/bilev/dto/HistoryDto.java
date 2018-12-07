package com.bilev.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true, of = {})
public class HistoryDto extends AbstractDto {
    private String name;

    private Double price;

    private Date date;
}
