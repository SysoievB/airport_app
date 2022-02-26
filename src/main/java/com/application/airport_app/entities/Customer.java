package com.application.airport_app.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name="customers")
public class Customer extends BaseEntity {

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;
    @Transient
    private Integer age;//should be removed from the constructors

    @OneToMany
    private Set<Ticket> tickets;

    @OneToOne
    private Account account;


    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
