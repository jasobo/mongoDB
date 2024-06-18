package de.sobotta.service;

import de.sobotta.Pojo.Address;
import de.sobotta.Pojo.Bank;
import de.sobotta.Pojo.Customer;
import de.sobotta.Pojo.PaymentMethod;
import de.sobotta.repository.CustomerMongoDBRepository;
import de.sobotta.repository.AddressMongoDBRepository;
import de.sobotta.repository.PaymentMongoDBRepository;
import de.sobotta.repository.BankMongoDBRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    CustomerMongoDBRepository customerMongoDBRepository;
    @Mock
    AddressMongoDBRepository addressMongoDBRepository;
    @Mock
    PaymentMongoDBRepository paymentMongoDBRepository;
    @Mock
    BankMongoDBRepository bankMongoDBRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private Address address;
    private PaymentMethod paymentMethod;
    private Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        bank.setName("Test Bank");
        bank.setArea("Mittelfranken Sued");

        paymentMethod = new PaymentMethod();
        paymentMethod.setIban("123456789");
        paymentMethod.setBic("123456789");
        paymentMethod.setBank(bank);

        address = new Address();
        address.setStreet("Test Street");
        address.setHouseNumber("1");
        address.setZip("1234");
        address.setCity("Test City");
        address.setCountry("Test Country");

        customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setAddress(address);
        customer.setPaymentMethod(paymentMethod);
    }

    @Test
    void testGetAllCustomers() {
        when(customerMongoDBRepository.findAll()).thenReturn(Arrays.asList(customer));

        List<Customer> customers = customerService.getAllCustomers();

        assertNotNull(customers);
        assertEquals(1, customers.size());
        assertEquals("John", customers.get(0).getFirstName());
        verify(customerMongoDBRepository, times(1)).findAll();
    }

    @Test
    void testCreateCustomer() {
        customerService.createCustomer(customer);

        verify(addressMongoDBRepository, times(1)).save(address);
        verify(paymentMongoDBRepository, times(1)).save(paymentMethod);
        verify(bankMongoDBRepository, times(1)).save(bank);
        verify(customerMongoDBRepository, times(1)).save(customer);
    }

    @Test
    void testGetCustomerById() {
        when(customerMongoDBRepository.findById("1")).thenReturn(Optional.of(customer));

        Optional<Customer> foundCustomer = customerService.getCustomerById("1");

        assertTrue(foundCustomer.isPresent());
        assertEquals("John", foundCustomer.get().getFirstName());
        verify(customerMongoDBRepository, times(1)).findById("1");
    }

    @Test
    void testGetByLastName() {
        when(customerMongoDBRepository.findByLastName("Doe")).thenReturn(Arrays.asList(customer));

        List<Customer> customers = customerService.getByLastName("Doe");

        assertNotNull(customers);
        assertEquals(1, customers.size());
        verify(customerMongoDBRepository, times(1)).findByLastName("Doe");
    }

    @Test
    void testUpdateCustomer() {
        when(customerMongoDBRepository.findById("1")).thenReturn(Optional.of(customer));

        Customer updatedCustomer = new Customer();
        updatedCustomer.setFirstName("Jane");
        updatedCustomer.setLastName("Smith");
        updatedCustomer.setAddress(address);
        updatedCustomer.setPaymentMethod(paymentMethod);

        customerService.updateCustomer("1", updatedCustomer);

        verify(customerMongoDBRepository, times(1)).findById("1");
        verify(addressMongoDBRepository, times(1)).save(address);
        verify(paymentMongoDBRepository, times(1)).save(paymentMethod);
        verify(bankMongoDBRepository, times(1)).save(bank);
        verify(customerMongoDBRepository, times(1)).save(customer);

        assertEquals("Jane", customer.getFirstName());
        assertEquals("Smith", customer.getLastName());
    }

    @Test
    void testDeleteCustomer() {
        customerService.delete("1");

        verify(customerMongoDBRepository, times(1)).deleteById("1");
    }
}
