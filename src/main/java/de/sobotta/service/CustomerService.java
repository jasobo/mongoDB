package de.sobotta.service;

import de.sobotta.DTO.CustomerDTO;
import de.sobotta.Pojo.Customer;
import de.sobotta.Response.CustomerResponse;
import de.sobotta.repository.CustomerMongoDBRepository;
import de.sobotta.util.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerMapper mapper = CustomerMapper.INSTANCE;
    private final CustomerMongoDBRepository customerMongoDBRepository;

    @Autowired
    public CustomerService(CustomerMongoDBRepository customerMongoDBRepository) {
        this.customerMongoDBRepository = customerMongoDBRepository;
    }

    private String generateCustomId() {
        return "UUID" + UUID.randomUUID().toString().replace("-", "").substring(0, 9).toUpperCase();
    }

    public List<CustomerResponse> getAllCustomers() {

        List<Customer> allCustomers = customerMongoDBRepository.findAll();
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for (Customer customer : allCustomers) {
            customerResponses.add(mapper.forResponse(customer));
        }
        return customerResponses;
    }

    public void createCustomer(CustomerDTO customerDTO) {
        System.out.println("Creating new customer: " + customerDTO);

        Customer customer = mapper.toPojo(customerDTO);
        customer.setId(generateCustomId());
        customerMongoDBRepository.save(customer);

        System.out.println("Customer created: " + customer);
    }

    public CustomerDTO getCustomerById(String id) {
        return customerMongoDBRepository.findById(id)
                .map(mapper::pojoToCustomerDTO)
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + id));

    }

    public List<CustomerResponse> getByLastName(String name) {
        return customerMongoDBRepository.findByLastName(name)
                .stream()
                .map(mapper::forResponse)
                .collect(Collectors.toList());
    }


    public void updateCustomer(String id, CustomerDTO customerDTO) {
        Optional<Customer> existingCustomerOpt = customerMongoDBRepository.findById(id);
        if(existingCustomerOpt.isPresent()) {
            Customer existingCustomer = existingCustomerOpt.get();
            Customer updatedCustomer = mapper.toPojo(customerDTO);
            updatedCustomer.setId(existingCustomer.getId());

            customerMongoDBRepository.save(updatedCustomer);
        } else {
            throw new RuntimeException("Customer not found with id " + id);
        }
    }

    public void delete(String id) {
        if (!customerMongoDBRepository.existsById(id)) {
            throw new RuntimeException("Customer not found with id " + id);
        }
        customerMongoDBRepository.deleteById(id);
    }

}
