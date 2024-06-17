package de.sobotta.controller;

import de.sobotta.Pojo.Customer;
import de.sobotta.repository.CustomerMongoDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerMongoDBRepository customerMongoDBRepository;

    @Autowired
    public CustomerController(CustomerMongoDBRepository customerMongoDBRepository) {
        this.customerMongoDBRepository = customerMongoDBRepository;
    }

    @PostMapping
    public void createNewAlbum(@RequestBody Customer customer) {
        customerMongoDBRepository.save(customer);
    }

    @GetMapping("/all")
    public List<Customer> showAll() {
        return customerMongoDBRepository.findAll();
    }

@GetMapping("/{lastname}")
public List<Customer> findByLastName(@PathVariable String lastname){
    return customerMongoDBRepository.findByLastName(lastname);
}

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        customerMongoDBRepository.deleteById(String.valueOf(id));
    }
}