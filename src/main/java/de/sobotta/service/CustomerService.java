package de.sobotta.service;

import de.sobotta.Pojo.Customer;
import de.sobotta.repository.AddressMongoDBRepository;
import de.sobotta.repository.CustomerMongoDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerMongoDBRepository customerMongoDBRepository;
    private final AddressMongoDBRepository addressMongoDBRepository;

    @Autowired
    public CustomerService(CustomerMongoDBRepository customerMongoDBRepository, AddressMongoDBRepository addressMongoDBRepository) {
        this.customerMongoDBRepository = customerMongoDBRepository;
        this.addressMongoDBRepository = addressMongoDBRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerMongoDBRepository.findAll();
    }

    public void createCustomer(Customer customer) {
        if (customer.getAddress() != null) {
            addressMongoDBRepository.save(customer.getAddress());
        }
        customerMongoDBRepository.save(customer);
    }

    public Optional<Customer> getCustomerById(String id) {
        return customerMongoDBRepository.findById(id);
    }

    public List<Customer> getByLastName(String name) {
        return customerMongoDBRepository.findByLastName(name);
    }

    public void updateCustomer(String id, Customer customer) {
        Optional<Customer> existingCustomerOpt = customerMongoDBRepository.findById(id);
        if(existingCustomerOpt.isPresent()) {
            Customer existingCustomer = existingCustomerOpt.get();

            existingCustomer.setFirstName(customer.getFirstName());
            existingCustomer.setLastName(customer.getLastName());

            if (customer.getAddress() != null) {
                addressMongoDBRepository.save(customer.getAddress());
                existingCustomer.setAddress(customer.getAddress());
            }

            customerMongoDBRepository.save(existingCustomer);
        } else {
            throw new RuntimeException("Customer not found with id " + id);
        }
    }


    public void delete(String id) {
        customerMongoDBRepository.deleteById(id);
    }
}
