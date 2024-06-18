package de.sobotta.repository;

import de.sobotta.Pojo.PaymentMethod;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentMongoDBRepository extends MongoRepository<PaymentMethod, String> {
}
