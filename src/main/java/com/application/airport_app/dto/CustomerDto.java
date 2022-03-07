package com.application.airport_app.dto;

import com.application.airport_app.entities.Customer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDto {

    private Long id;
    private String firstName;
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private List<TicketDto> ticketDtos;


    public static CustomerDto fromCustomer(Customer customer) {

        var ticketDtosList = customer.getTickets().stream()
                .map(TicketDto::fromTicket)
                .collect(Collectors.toCollection(ArrayList::new));

        return new CustomerDto(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getDateOfBirth(),
                ticketDtosList
        );
    }

    public static List<CustomerDto> toCustomerDto(List<Customer> customers) {

        return customers.stream()
                .map(CustomerDto::fromCustomer)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
