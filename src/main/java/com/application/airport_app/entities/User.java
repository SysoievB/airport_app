package com.application.airport_app.entities;

import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.Period;

public class User {

    private LocalDate dateOfBirth;
    @Transient
    private Integer age;//should be removed from the constructors

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getAge() {
        return Period.between(this.dateOfBirth,LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
