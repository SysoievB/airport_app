package com.application.airport_app.dto;

import com.application.airport_app.entities.Ticket;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketDto {

    private Long id;
    private Double price;

    public static TicketDto fromTicket(Ticket ticket){

        return new TicketDto(ticket.getId(),ticket.getPrice());
    }

    public static List<TicketDto> toTicketDto(List<Ticket> tickets) {

        return tickets.stream()
                .map(TicketDto::fromTicket)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
