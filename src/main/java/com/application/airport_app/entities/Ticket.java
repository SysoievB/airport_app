package com.application.airport_app.entities;

import java.util.Date;

public class Ticket extends BaseEntity{

    private Flight flight;

    private Integer price;

    private Date purchaseTime;
}
