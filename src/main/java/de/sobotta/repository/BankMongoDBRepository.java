package de.sobotta.repository;

import de.sobotta.Pojo.Bank;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BankMongoDBRepository extends MongoRepository<Bank, String> {
}
