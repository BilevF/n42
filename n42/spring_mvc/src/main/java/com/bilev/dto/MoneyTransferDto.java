package com.bilev.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
public class MoneyTransferDto {

    private String notification_type;

    private String operation_id;

    private String amount;

    private String withdraw_amount;

    private String currency;

    private String datetime;

    private String sender;

    private Boolean codepro;

    private String label;

    private String sha1_hash;

    private Boolean unaccepted;
}

