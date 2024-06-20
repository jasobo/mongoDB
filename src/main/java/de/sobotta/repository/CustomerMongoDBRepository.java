package de.sobotta.repository;

import de.sobotta.Entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerMongoDBRepository extends MongoRepository<Customer, String> {
    List<Customer> findByLastName(String lastName);
}
