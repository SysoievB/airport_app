package com.application.airport_app.service.impl;

import com.application.airport_app.entities.AccountStatus;
import com.application.airport_app.entities.Ticket;
import com.application.airport_app.repository.TicketRepository;
import com.application.airport_app.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static com.application.airport_app.util.DateUtil.getCurrentDate;

@Service
@Slf4j
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public void save(Ticket ticket) {
        if (ticket.getStatus() == null) ticket.setStatus(AccountStatus.ACTIVE);

        ticket.setCreated(getCurrentDate());
        ticket.setUpdated(getCurrentDate());

        ticketRepository.save(ticket);
        log.info("IN TicketServiceImpl save {}", ticket);
    }

    @Override
    public void update(Long id, Ticket updateTicket) {
        Ticket ticket = getById(id);

        if (updateTicket.getPrice() != null) ticket.setPrice(updateTicket.getPrice());

        if (updateTicket.getDeparture() != null) ticket.setDeparture(updateTicket.getDeparture());

        if (updateTicket.getArrival() != null) ticket.setArrival(updateTicket.getArrival());

        if (updateTicket.getStatus() != null) ticket.setStatus(updateTicket.getStatus());

        if (updateTicket.getPurchaseTime() != null) ticket.setPurchaseTime(updateTicket.getPurchaseTime());

        ticket.setUpdated(getCurrentDate());
        ticketRepository.save(ticket);
        log.info("IN TicketServiceImpl update {}", ticket);
    }

    @Override
    public void delete(Long id) {
        Ticket ticket = getById(id);
        ticket.setUpdated(getCurrentDate());
        ticket.setStatus(AccountStatus.DELETED);

        ticketRepository.save(ticket);
        log.info("IN TicketServiceImpl delete {}", ticket);
    }

    @Override
    public Ticket getById(Long id) {
        Ticket ticket = ticketRepository
                .findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("Ticket not found by this id :: " + id)
                );
        log.info("IN getById - ticket: {} found by id: {}", ticket,id);

        return ticket;
    }

    @Override
    public List<Ticket> list() {
        log.info("IN TicketServiceImpl list");

        return ticketRepository.findAll();
    }
}
