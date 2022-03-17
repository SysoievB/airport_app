package com.application.airport_app.service.impl;

import com.application.airport_app.entities.AccountStatus;
import com.application.airport_app.entities.Customer;
import com.application.airport_app.entities.Ticket;
import com.application.airport_app.repository.CustomerRepository;
import com.application.airport_app.repository.TicketRepository;
import com.application.airport_app.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.application.airport_app.util.DateUtil.getCurrentDate;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, TicketRepository ticketRepository) {
        this.customerRepository = customerRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public void save(Customer customer) {
        if (customer.getStatus() == null) customer.setStatus(AccountStatus.ACTIVE);

        customer.setCreated(getCurrentDate());
        customer.setUpdated(getCurrentDate());

        Set<Ticket> customerTickets = ticketRepository.findAll().stream()
                .map(ticket -> ticketRepository.findById(ticket.getId()).get())
                .collect(Collectors.toSet());

        customer.setTickets(customerTickets);
        customerRepository.save(customer);
        log.info("IN CustomerServiceImpl save {}", customer);
    }

    @Override
    public void update(Long id, Customer updatedCustomer) {
        Customer customer = getById(id);

        if (updatedCustomer.getFirstName() != null) customer.setFirstName(updatedCustomer.getFirstName());

        if (updatedCustomer.getLastName() != null) customer.setLastName(updatedCustomer.getLastName());

        if (updatedCustomer.getStatus() != null) customer.setStatus(updatedCustomer.getStatus());

        if (updatedCustomer.getTickets() != null) {
            Set<Ticket> customerTickets = updatedCustomer.getTickets().stream()
                    .map(ticket -> ticketRepository.findById(ticket.getId()).get())
                    .collect(Collectors.toSet());

            customer.setTickets(customerTickets);
        }

        customerRepository.save(customer);

        log.info("In CustomerServiceImpl update {}", customer);
    }

    @Override
    public void delete(Long id) {
        Customer customer = getById(id);
        customer.setStatus(AccountStatus.DELETED);
        customer.setUpdated(getCurrentDate());

        customerRepository.save(customer);

        log.info("IN CustomerServiceImpl delete {}", customer);
    }

    @Override
    public Customer getById(Long id) {
        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("Customer not found by this id :: " + id)
                );

        log.info("IN getById - customer: {} found by id: {}", customer, id);

        return customer;
    }

    @Override
    public List<Customer> list() {
        log.info("IN CustomerServiceImpl list");

        return customerRepository.findAll();
    }
}
