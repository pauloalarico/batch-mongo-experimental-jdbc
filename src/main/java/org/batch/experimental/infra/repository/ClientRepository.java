package org.batch.experimental.infra.repository;

import org.batch.experimental.infra.persistance.ClientDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepository extends MongoRepository<ClientDocument, String> {
}
