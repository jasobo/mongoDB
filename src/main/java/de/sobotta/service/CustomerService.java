package de.sobotta.service;

import de.sobotta.Pojo.Address;
import de.sobotta.Pojo.Customer;
import de.sobotta.Pojo.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class CustomerService {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CustomerService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Customer> getAllCustomers() {
        return mongoTemplate.findAll(Customer.class);
    }

    public void createCustomer(Customer customer) {
        if (customer.getAddress() != null) {
            mongoTemplate.save(customer.getAddress());
        }
        if (customer.getPaymentMethod() != null){
            savePaymentMethod(customer.getPaymentMethod());
        }
        mongoTemplate.save(customer);
    }

    public Optional<Customer> getCustomerById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, Customer.class));
    }

    public List<Customer> getByLastName(String name) {
        return mongoTemplate.find(query(where("lastName").is(name)), Customer.class);
    }


    public void updateCustomer(String id, Customer customer) {
        Optional<Customer> existingCustomerOpt = getCustomerById(id);
        if(existingCustomerOpt.isPresent()) {
            Customer existingCustomer = existingCustomerOpt.get();

            existingCustomer.setFirstName(customer.getFirstName());
            existingCustomer.setLastName(customer.getLastName());

            updateAddress(existingCustomer, customer.getAddress());
            updatePaymentMethod(existingCustomer, customer.getPaymentMethod());

            mongoTemplate.save(existingCustomer);
        } else {
            throw new RuntimeException("Customer not found with id " + id);
        }
    }

    public void delete(String id) {
        mongoTemplate.remove(query(where("_id").is(id)), Customer.class);
    }

    private void updateAddress(Customer existingCustomer, Address newAddress) {
        if (newAddress != null) {
            mongoTemplate.save(newAddress);
            existingCustomer.setAddress(newAddress);
        }
    }

    private void updatePaymentMethod(Customer existingCustomer, PaymentMethod newPaymentMethod) {
        if (newPaymentMethod != null) {
            if (newPaymentMethod.getBank() != null) {
                mongoTemplate.save(newPaymentMethod.getBank());
                newPaymentMethod.setBank(newPaymentMethod.getBank());
            }
            mongoTemplate.save(newPaymentMethod);
            existingCustomer.setPaymentMethod(newPaymentMethod);
        }
    }

    private void savePaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod.getBank() != null) {
            mongoTemplate.save(paymentMethod.getBank());
        }
        mongoTemplate.save(paymentMethod);
    }
}
