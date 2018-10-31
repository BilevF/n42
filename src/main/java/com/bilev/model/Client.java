package com.bilev.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("ROLE_CLIENT")
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class Client extends User {
    @OneToMany(mappedBy = "client",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<Contract> contracts = new ArrayList<>();
}
