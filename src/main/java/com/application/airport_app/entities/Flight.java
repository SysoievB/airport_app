package com.application.airport_app.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name="flights")
public class Flight extends BaseEntity {

    private String fromCity;

    private String toCity;

    @ManyToMany
    private Set<Ticket> tickets = new HashSet<>();
}
