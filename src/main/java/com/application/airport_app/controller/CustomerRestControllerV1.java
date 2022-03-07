package com.application.airport_app.controller;

import com.application.airport_app.dto.CustomerDto;
import com.application.airport_app.entities.Customer;
import com.application.airport_app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerRestControllerV1 {

    private final CustomerService customerService;

    @Autowired
    public CustomerRestControllerV1(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> get(@PathVariable Long id) {

        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        Customer customer = this.customerService.getById(id);

        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(CustomerDto.fromCustomer(customer));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAll() {

        List<Customer> customers = this.customerService.list();

        if (customers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(CustomerDto.toCustomerDto(customers));
    }

    @PostMapping
    public ResponseEntity<Customer> save(@RequestBody Customer customer) {

        if (customer == null) {
            return ResponseEntity.badRequest().build();
        }

        this.customerService.save(customer);

        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody Customer customer) {

        if (id == null || customer == null) {
            return ResponseEntity.badRequest().build();
        }

        this.customerService.update(id, customer);

        return ResponseEntity.accepted().body(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> delete(@PathVariable Long id) {

        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        Customer customer = this.customerService.getById(id);

        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        this.customerService.delete(id);

        return ResponseEntity.accepted().build();
    }
}