package de.sobotta.controller;

import de.sobotta.Pojo.Customer;
import de.sobotta.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void createNewAlbum(@RequestBody Customer customer) {
        customerService.createCustomer(customer);
    }

    @GetMapping("/all")
    public List<Customer> getAll() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{lastname}")
    public List<Customer> findByLastName(@PathVariable String lastname) {
        return customerService.getByLastName(lastname);
    }

    @GetMapping("/id/{id}")
    public Customer findById(@PathVariable String id) {
        return customerService.getCustomerById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping("{id}")
    public void updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        customerService.delete(id);
    }
}