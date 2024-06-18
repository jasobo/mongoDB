package de.sobotta.service;

import de.sobotta.Pojo.Address;
import de.sobotta.Pojo.Customer;
import de.sobotta.Pojo.PaymentMethod;
import de.sobotta.repository.AddressMongoDBRepository;
import de.sobotta.repository.BankMongoDBRepository;
import de.sobotta.repository.CustomerMongoDBRepository;
import de.sobotta.repository.PaymentMongoDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerMongoDBRepository customerMongoDBRepository;
    private final AddressMongoDBRepository addressMongoDBRepository;
    private final PaymentMongoDBRepository paymentMongoDBRepository;
    private final BankMongoDBRepository bankMongoDBRepository;

    @Autowired
    public CustomerService(CustomerMongoDBRepository customerMongoDBRepository, AddressMongoDBRepository addressMongoDBRepository, PaymentMongoDBRepository paymentMongoDBRepository, BankMongoDBRepository bankMongoDBRepository) {
        this.customerMongoDBRepository = customerMongoDBRepository;
        this.addressMongoDBRepository = addressMongoDBRepository;
        this.paymentMongoDBRepository = paymentMongoDBRepository;
        this.bankMongoDBRepository = bankMongoDBRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerMongoDBRepository.findAll();
    }

    public void createCustomer(Customer customer) {
        if (customer.getAddress() != null) {
            addressMongoDBRepository.save(customer.getAddress());
        }
        if (customer.getPaymentMethod() != null){
            savePaymentMethod(customer.getPaymentMethod());
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

            updateAddress(existingCustomer, customer.getAddress());
            updatePaymentMethod(existingCustomer, customer.getPaymentMethod());

            customerMongoDBRepository.save(existingCustomer);
        } else {
            throw new RuntimeException("Customer not found with id " + id);
        }
    }


    public void delete(String id) {
        customerMongoDBRepository.deleteById(id);
    }

    private void updateAddress(Customer existingCustomer, Address newAddress) {
        if (newAddress != null) {
            addressMongoDBRepository.save(newAddress);
            existingCustomer.setAddress(newAddress);
        }
    }

    private void updatePaymentMethod(Customer existingCustomer, PaymentMethod newPaymentMethod) {
        if (newPaymentMethod != null) {
            if (newPaymentMethod.getBank() != null) {
                bankMongoDBRepository.save(newPaymentMethod.getBank());
                newPaymentMethod.setBank(newPaymentMethod.getBank());
            }
            paymentMongoDBRepository.save(newPaymentMethod);
            existingCustomer.setPaymentMethod(newPaymentMethod);
        }
    }

    private void savePaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod.getBank() != null) {
            bankMongoDBRepository.save(paymentMethod.getBank());
        }
        paymentMongoDBRepository.save(paymentMethod);
    }
}
