package org.batch.experimental.infra.batch.writer;

import lombok.RequiredArgsConstructor;
import org.batch.experimental.domain.entitie.Client;
import org.batch.experimental.infra.persistance.ClientDocument;
import org.batch.experimental.infra.persistance.ClientMapper;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MongoWriter implements ItemWriter<Client> {
    private final ClientMapper clientMapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public void write(Chunk<? extends Client> chunk) throws Exception {
        List<ClientDocument> documents = chunk.getItems()
                .stream()
                .map(clientMapper::toDocument)
                .toList();

        mongoTemplate
                .bulkOps(BulkOperations.BulkMode.UNORDERED, ClientDocument.class)
                .insert(documents)
                .execute();
    }
}
