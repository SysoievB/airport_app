package com.application.airport_app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tickets")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ticket extends BaseEntity {

    @Column(name = "price")
    private Double price;

    @Column(name = "departure")
    private String departure;

    @Column(name = "arrival")
    private String arrival;

    @Column(name = "purchase_time")
    private Date purchaseTime;

    @JsonIgnore
    @ManyToMany(mappedBy = "tickets")
    private List<Customer> customers;

    public Ticket(Long id, String departure, String arrival) {
        this.id = id;
        this.departure = departure;
        this.arrival = arrival;
    }

    public Ticket(String departure, String arrival) {
        this.departure = departure;
        this.arrival = arrival;
    }

    public Ticket(Long id) {
        this.id = id;
    }
}
