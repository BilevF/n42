package com.bilev.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="HISTORY")
@Setter
@Getter
@EqualsAndHashCode(callSuper = true, of = {})
@ToString(callSuper = true, exclude =  {"contract"})
public class History extends AbstractModel {

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "DATE", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "CONTRACT_ID")
    private Contract contract;
}
