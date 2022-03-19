package com.application.airport_app.controller;

import com.application.airport_app.dto.TicketDto;
import com.application.airport_app.entities.Ticket;
import com.application.airport_app.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class TicketRestControllerV1Test {

    @Mock
    private TicketService underTestTicketService;

    @InjectMocks
    private TicketRestControllerV1 underTestTicketRestControllerV1;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void itShouldGetTicket() {
        // Given
        Ticket ticket = new Ticket(1L, "test", "test");

        // When
        when(underTestTicketService.getById(ticket.getId())).thenReturn(ticket);
        var response = underTestTicketRestControllerV1.get(ticket.getId());

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket.getDeparture(), Objects.requireNonNull(response.getBody()).getDeparture());
        assertEquals(ticket.getId(), response.getBody().getId());
    }

    @Test
    public void itShouldGetFailTicket() {
        // Given
        Long id = 1L;

        // When
        var response1 = underTestTicketRestControllerV1.get(null);
        var response2 = underTestTicketRestControllerV1.get(id);

        when(underTestTicketService.getById(id)).thenReturn(null);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());
    }

    @Test
    void itShouldGetAllTickets() {
        // Given
        var tickets = List.of(
                new Ticket(1L, "test1", "test1"),
                new Ticket(2L, "test2", "test2")
        );

        var firstTicket = tickets.stream()
                .filter(t -> t.getId() == 1L)
                .findFirst().get();

        var secondTicket = tickets.stream()
                .filter(t -> t.getId() == 2L)
                .findFirst().get();

        // When
        when(underTestTicketService.list()).thenReturn(tickets);

        ResponseEntity<List<TicketDto>> response = underTestTicketRestControllerV1.getAll();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tickets.size(), Objects.requireNonNull(response.getBody()).size());
        assertEquals(firstTicket.getDeparture(), response.getBody().get(0).getDeparture());
        assertEquals(secondTicket.getDeparture(), response.getBody().get(1).getDeparture());
    }

    @Test
    public void itShouldGetAllTicketsFail() {
        // When
        when(underTestTicketService.list()).thenReturn(new ArrayList<>());

        ResponseEntity<List<TicketDto>> response = underTestTicketRestControllerV1.getAll();
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void itShouldSaveTicket() {
        // Given
        Ticket savedTicket = new Ticket("test", "test");

        // When
        var response = underTestTicketRestControllerV1.save(savedTicket);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedTicket.getDeparture(), Objects.requireNonNull(response.getBody()).getDeparture());
    }

    @Test
    public void itShouldFailWhenSaveTicket() {
        // When
        ResponseEntity<Ticket> response = underTestTicketRestControllerV1.save(null);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void itShouldUpdateTicket() {
        // Given
        Ticket ticket = new Ticket(1L, "test", "test");
        Ticket updateTicket = new Ticket(2L);

        // When
        ResponseEntity<Ticket> response = underTestTicketRestControllerV1.update(1L, updateTicket);

        // Then
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(updateTicket.getDeparture(), Objects.requireNonNull(response.getBody()).getDeparture());
        assertEquals(updateTicket.getArrival(), Objects.requireNonNull(response.getBody()).getArrival());
    }

    @Test
    public void itShouldFailWhenUpdateTicket() {
        // When
        ResponseEntity<Ticket> response = underTestTicketRestControllerV1.update(null, null);

        //Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void itShouldDeleteTicket() {
        // Given
        Ticket ticket = new Ticket(1L, "test", "test");

        // When
        when(underTestTicketService.getById(ticket.getId())).thenReturn(ticket);

        ResponseEntity<Ticket> response = underTestTicketRestControllerV1.delete(ticket.getId());

        // Then
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    public void deleteFail() {
        // Given
        Long id = 1L;

        // When
        when(underTestTicketService.getById(id)).thenReturn(null);

        ResponseEntity<Ticket> response = underTestTicketRestControllerV1.delete(id);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}