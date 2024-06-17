package de.sobotta.repository;

import de.sobotta.Pojo.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressMongoDBRepository extends MongoRepository<Address, String> {
}
