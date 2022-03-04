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

    @Column(name = "purchase_time")
    private Date purchaseTime;

    @JsonIgnore
    @ManyToMany(mappedBy = "tickets")
    private List<Customer> customers;
}
