package com.application.airport_app.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @Column(name = "price")
    private Integer price;

    @Column(name = "purchase_time")
    private Date purchaseTime;


    @ManyToMany
    private Set<Flight> flights = new HashSet<>();
}
