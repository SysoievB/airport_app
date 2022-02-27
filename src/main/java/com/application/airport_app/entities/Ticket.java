package com.application.airport_app.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tickets")
public class Ticket extends BaseEntity {

    @OneToOne
    private Flight flight;
    private Integer price;
    private Date purchaseTime;


    @ManyToMany
    private Set<Flight> flights = new HashSet<>();
}
