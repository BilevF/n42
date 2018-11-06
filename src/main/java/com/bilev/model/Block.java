package com.bilev.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="BLOCK")
@Setter
@Getter
@EqualsAndHashCode(callSuper = true, of = {})
public class Block extends AbstractModel {

    public enum BlockType {
        NON, CLIENT_BLOCK, ADMIN_BLOCK
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private BlockType blockType;

}
