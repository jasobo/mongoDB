package de.sobotta.server;

import de.sobotta.DTO.CustomerDTO;
import de.sobotta.Entity.Customer;
import de.sobotta.repository.CustomerMongoDBRepository;
import de.sobotta.service.CustomerService;
import de.sobotta.util.CustomerMapper;
import de.sobotta.util.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CSTest {

    @Mock
    CustomerMongoDBRepository customerMongoDBRepository;

    @Mock
    CustomerMapper customerMapper;

    @InjectMocks
    CustomerService customerService;

    private Customer customer;
    private CustomerDTO customerDTO;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId("UUID123456789");
        customer.setFirstName("Mario");
        customer.setLastName("Mario");

        customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Luigi");
        customerDTO.setLastName("Mario");
    }

    @Test
    void testDeleteCustomer() {
        when(customerMongoDBRepository.existsById(anyString())).thenReturn(true);

        customerService.delete("UUID123456789");

        verify(customerMongoDBRepository, times(1)).deleteById("UUID123456789");
    }

    @Test
    void testDeleteCustomerNotFound() {
        when(customerMongoDBRepository.existsById(anyString())).thenReturn(false);

        assertThrows(GlobalExceptionHandler.NotFoundException.class, () -> {
            customerService.delete("UUID123456789");
        });

        verify(customerMongoDBRepository, never()).deleteById(anyString());
    }
}
