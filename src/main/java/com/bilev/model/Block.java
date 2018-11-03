package com.bilev.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="BLOCK")
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class Block extends AbstractModel {

    public enum BlockType {
        NON, CLIENT_BLOCK, ADMIN_BLOCK
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private BlockType blockType;

    @OneToMany(mappedBy = "block")
    private List<Contract> contracts = new ArrayList<>();
}
