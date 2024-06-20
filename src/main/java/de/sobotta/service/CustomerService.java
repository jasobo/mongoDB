package de.sobotta.service;

import de.sobotta.DTO.CustomerDTO;
import de.sobotta.Entity.Customer;
import de.sobotta.Response.CustomerResponse;
import de.sobotta.repository.CustomerMongoDBRepository;
import de.sobotta.util.CustomerMapper;
import de.sobotta.util.GlobalExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

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
        logger.info("All customers found");
        return customerResponses;
    }

    public void createCustomer(CustomerDTO customerDTO) {
        Customer customer = mapper.toPojo(customerDTO);
        customer.setId(generateCustomId());
        logger.info("Customer created with id {}", customer.getId());
        customerMongoDBRepository.save(customer);
    }

    public CustomerDTO getCustomerById(String id) {
        return customerMongoDBRepository.findById(id)
                .map(mapper::pojoToCustomerDTO)
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + id));

    }

    public List<CustomerResponse> getByLastName(String name) {
        logger.info("Searching for customer with last name {}",name);
        return customerMongoDBRepository.findByLastName(name)
                .stream()
                .map(mapper::forResponse)
                .collect(Collectors.toList());
    }


    public void updateCustomer(String id, CustomerDTO customerDTO) {
        try {
            Optional<Customer> existingCustomerOpt = customerMongoDBRepository.findById(id);
            if (existingCustomerOpt.isPresent()) {
                Customer existingCustomer = existingCustomerOpt.get();
                Customer updatedCustomer = mapper.toPojo(customerDTO);
                updatedCustomer.setId(existingCustomer.getId());

                customerMongoDBRepository.save(updatedCustomer);
                logger.info("Customer with id {} updated successfully.", id);
            } else {
                logger.error("Customer not found with id {}", id);
                throw new GlobalExceptionHandler.NotFoundException("Customer not found with id " + id);
            }
        } catch (GlobalExceptionHandler.NotFoundException ex) {
            logger.error("Failed to update customer with id: {}", id, ex);
            throw ex;
        } catch (Exception ex) {
            logger.error("Unexpected error while updating customer with id: {}", id, ex);
            throw new RuntimeException("Unexpected error while updating customer with id " + id, ex);
        }
    }


    public void delete(String id) {
        if (!customerMongoDBRepository.existsById(id)) {
            throw new GlobalExceptionHandler.NotFoundException("Customer not found with id " + id);
        }
        customerMongoDBRepository.deleteById(id);
    }

}
