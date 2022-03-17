package com.application.airport_app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "customers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Transient
    private Integer age;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "customers_tickets", joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "ticket_id"))
    @OrderBy(value = "id ASC")
    private Set<Ticket> tickets;

    public Customer(String firstName, String lastName, AccountStatus status, Set<Ticket> tickets) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.tickets = tickets;
    }

    public Customer(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Customer(Long id) {
        this.id = id;
    }

    public Customer(Long id, String firstName, String lastName, Set<Ticket> tickets) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.tickets = tickets;
    }

    public Customer(String firstName, String lastName, Set<Ticket> tickets) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.tickets = tickets;
    }

    public Integer getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }
}
