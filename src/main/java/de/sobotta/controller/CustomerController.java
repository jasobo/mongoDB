package de.sobotta.controller;

import de.sobotta.DTO.CustomerDTO;
import de.sobotta.Response.CustomerResponse;
import de.sobotta.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        System.out.println("Inside createCustomer" + customerDTO);
        customerService.createCustomer(customerDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/lastname/{lastname}")
    public ResponseEntity<List<CustomerResponse>> findByLastName(@PathVariable String lastname) {
        List<CustomerResponse> customers = customerService.getByLastName(lastname);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CustomerDTO> findById(@PathVariable String id) {
        CustomerDTO customer = customerService.getCustomerById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCustomer(@PathVariable String id, @Valid @RequestBody CustomerDTO customerDTO) {
        customerService.updateCustomer(id, customerDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        customerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}