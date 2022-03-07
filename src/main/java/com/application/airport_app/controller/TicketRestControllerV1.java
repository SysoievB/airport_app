package com.application.airport_app.controller;

import com.application.airport_app.dto.TicketDto;
import com.application.airport_app.entities.Ticket;
import com.application.airport_app.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketRestControllerV1 {

    private final TicketService ticketService;

    @Autowired
    public TicketRestControllerV1(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> get(@PathVariable Long id) {

        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        Ticket ticket = this.ticketService.getById(id);

        if (ticket == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(TicketDto.fromTicket(ticket));
    }

    @GetMapping
    public ResponseEntity<List<TicketDto>> getAll() {

        List<Ticket> tickets = this.ticketService.list();

        if (tickets.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(TicketDto.toTicketDto(tickets));
    }

    @PostMapping
    public ResponseEntity<Ticket> save(@RequestBody Ticket ticket) {

        if (ticket == null) {
            return ResponseEntity.badRequest().build();
        }

        this.ticketService.save(ticket);

        return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> update(@PathVariable Long id, @RequestBody Ticket ticket) {

        if (id == null || ticket == null) {
            return ResponseEntity.badRequest().build();
        }

        this.ticketService.update(id, ticket);

        return ResponseEntity.accepted().body(ticket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Ticket> delete(@PathVariable Long id) {

        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        Ticket ticket = this.ticketService.getById(id);

        if (ticket == null) {
            return ResponseEntity.notFound().build();
        }

        this.ticketService.delete(id);

        return ResponseEntity.accepted().build();
    }
}