package com.application.airport_app.service.impl;

import com.application.airport_app.entities.AccountStatus;
import com.application.airport_app.entities.Customer;
import com.application.airport_app.entities.Ticket;
import com.application.airport_app.repository.CustomerRepository;
import com.application.airport_app.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository underTestCustomerRepository;

    @Mock
    private TicketRepository underTestTicketRepository;

    @InjectMocks
    private CustomerServiceImpl underTestCustomerServiceImpl;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void itShouldSaveCustomer() {
        // Given
        var tickets = Set.of(
                new Ticket(1L, "test1", "test1"),
                new Ticket(2L, "test2", "test2")
        );

        Customer customer = new Customer("test", "test", tickets);

        // When
        var firstElement = tickets.stream().filter(t -> t.getId() == 1L).findFirst();
        var secondElement = tickets.stream().filter(t -> t.getId() == 2L).findFirst();
        when(underTestTicketRepository.findById(1L)).thenReturn(firstElement);
        when(underTestTicketRepository.findById(2L)).thenReturn(secondElement);

        underTestCustomerServiceImpl.save(customer);

        // Then
        assertEquals(customer.getStatus(), AccountStatus.ACTIVE);
        assertNotNull(customer.getCreated());
        assertNotNull(customer.getUpdated());

        verify(underTestCustomerRepository, atLeastOnce()).save(customer);
        customer.getTickets()
                .forEach(t ->
                        verify(underTestCustomerRepository, atLeastOnce()).findById(t.getId())
                );
    }

    @Test
    void itShouldUpdateCustomer() {
        // Given
        var tickets = Set.of(
                new Ticket(1L, "test1", "test1"),
                new Ticket(2L, "test2", "test2")
        );

        Customer customer = new Customer(1L,"test", "test", tickets);

        Customer updatedCustomer = new Customer(
                "updated",
                "updated",
                AccountStatus.NOT_ACTIVE,
                customer.getTickets()
        );

        // When
        var firstElement = tickets.stream().filter(t -> t.getId() == 1L).findFirst();
        var secondElement = tickets.stream().filter(t -> t.getId() == 2L).findFirst();
        when(underTestTicketRepository.findById(1L)).thenReturn(firstElement);
        when(underTestTicketRepository.findById(2L)).thenReturn(secondElement);
        when(underTestCustomerRepository.findById(1L)).thenReturn(Optional.of(customer));

        underTestCustomerServiceImpl.update(customer.getId(), updatedCustomer);

        // Then
        assertEquals(updatedCustomer.getFirstName(), customer.getFirstName());
        assertEquals(updatedCustomer.getLastName(), customer.getLastName());
        assertEquals(updatedCustomer.getTickets().size(), customer.getTickets().size());
        assertEquals(AccountStatus.NOT_ACTIVE, customer.getStatus());

        verify(underTestCustomerRepository, atLeastOnce()).save(customer);
    }

    @Test
    void itShouldDeleteCustomer() {
        // Given
        Customer customer = new Customer(1L, "test", "test");
        Date timeUpdatedBefore = customer.getUpdated();
        Date timeCreatedBefore = customer.getCreated();

        // When
        when(underTestCustomerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        underTestCustomerServiceImpl.delete(customer.getId());

        // Then
        assertEquals(customer.getStatus(), AccountStatus.DELETED);
        assertNotEquals(timeUpdatedBefore, customer.getUpdated());
        assertEquals(timeCreatedBefore, customer.getCreated());

        verify(underTestCustomerRepository, atLeastOnce()).save(customer);
    }

    @Test
    void itShouldGetByIdCustomer() {
        // Given
        Customer customer = new Customer(1L);

        // When
        when(underTestCustomerRepository.findById(1L)).thenReturn(Optional.of(customer));

        underTestCustomerServiceImpl.getById(customer.getId());

        // Then
        Long expectedId = 1L;
        assertEquals(expectedId, customer.getId());

        verify(underTestCustomerRepository, atLeastOnce()).findById(expectedId);
    }

    @Test
    void itShouldListCustomers() {
        // Given
        var customers = List.of(
                new Customer(1L),
                new Customer(2L)
        );

        // When
        when(underTestCustomerRepository.findAll()).thenReturn(customers);

        List<Customer> actualResult = underTestCustomerServiceImpl.list();
        // Then
        assertEquals(customers.size(), actualResult.size());

        verify(underTestCustomerRepository, atLeastOnce()).findAll();
    }
}