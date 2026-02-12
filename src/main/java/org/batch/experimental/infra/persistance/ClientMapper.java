package org.batch.experimental.infra.persistance;

import org.batch.experimental.domain.entitie.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientDocument toDocument(Client client) {
        return ClientDocument.builder()
                .externalId(client.getExternalId())
                .name(client.getName())
                .email(client.getEmail())
                .productType(client.getProductType())
                .amount(client.getAmount())
                .active(client.isActive())
                .creationDate(client.getCreationDate())
                .build();
    }

    public Client toDomain(ClientDocument document) {
        return Client.builder()
                .externalId(document.getExternalId())
                .name(document.getName())
                .email(document.getEmail())
                .productType(document.getProductType())
                .amount(document.getAmount())
                .active(document.isActive())
                .creationDate(document.getCreationDate())
                .build();
    }
}
