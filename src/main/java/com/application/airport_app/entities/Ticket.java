package com.application.airport_app.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tickets")
public class Ticket extends BaseEntity {

    @Column(name = "price")
    private Integer price;

    @Column(name = "purchase_time")
    private Date purchaseTime;
}
