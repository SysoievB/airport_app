package com.application.airport_app.service.impl;

import com.application.airport_app.entities.AccountStatus;
import com.application.airport_app.entities.Ticket;
import com.application.airport_app.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class TicketServiceImplTest {

    @Mock
    private TicketRepository underTestTicketRepository;

    @InjectMocks
    private TicketServiceImpl underTestTicketService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void itShouldSaveTicket() {
        //Given
        Ticket savedTicket = new Ticket("test", "test");

        //When
        underTestTicketService.save(savedTicket);

        //Then
        assertNotNull(savedTicket);
        assertEquals("test", savedTicket.getDeparture());
        assertEquals("test", savedTicket.getArrival());

        verify(underTestTicketRepository, atLeastOnce()).save(savedTicket);
    }

    @Test
    void itShouldUpdateTicket() {
        //Given
        Ticket ticket = new Ticket(1L, "test", "test");
        Ticket updatedTicket = new Ticket("updated", "updated");
        given(underTestTicketRepository.findById(ticket.getId())).willReturn(Optional.of(ticket));

        //When
        underTestTicketService.update(ticket.getId(), updatedTicket);

        //Then
        assertEquals("updated", ticket.getArrival());
        assertEquals("updated", ticket.getDeparture());

        verify(underTestTicketRepository, atLeastOnce()).save(ticket);
        verify(underTestTicketRepository, atLeastOnce()).findById(ticket.getId());
    }

    @Test
    void itShouldDeleteTicket() {
        //Given
        Ticket deletedTicket = new Ticket(1L, "test", "test");

        //When
        when(underTestTicketRepository.findById(deletedTicket.getId())).thenReturn(Optional.of(deletedTicket));
        underTestTicketService.delete(deletedTicket.getId());

        //Then
        assertEquals(deletedTicket.getStatus(), AccountStatus.DELETED);
        verify(underTestTicketRepository, atLeastOnce()).save(deletedTicket);
    }

    @Test
    void itShouldGetByIdTicket() {
        //Given
        Ticket getTicket = new Ticket(1L, "test", "test");

        //When
        when(underTestTicketRepository.findById(getTicket.getId())).thenReturn(Optional.of(getTicket));
        underTestTicketService.getById(getTicket.getId());

        //Then
        assertTrue(getTicket.getId() > 0);

        verify(underTestTicketRepository, atLeastOnce()).findById(getTicket.getId());
    }

    @Test
    void itShouldListTickets() {
        //Given
        List<Ticket> tickets = new ArrayList<>(Arrays.asList(new Ticket("test", "test")));

        given(underTestTicketRepository.findAll()).willReturn(tickets);

        //When
        List<Ticket> expected = underTestTicketService.list();

        //Then
        assertTrue(tickets.size() > 0);
        assertEquals(expected, tickets);

        verify(underTestTicketRepository, atLeastOnce()).findAll();
    }
}
