package org.batch.experimental.infra.persistance;

import org.batch.experimental.application.ClientFileDTO;
import org.batch.experimental.domain.entitie.Client;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;

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

    public Client toDomain(ClientFileDTO dto) {
        return Client.builder()
                .externalId(dto.externalId())
                .name(dto.name())
                .email(dto.email())
                .productType(dto.productType())
                .amount(dto.amount())
                .active(dto.active())
                .creationDate(convertDate(dto.creationDate()))
                .build();
    }

    private LocalDate convertDate(String date) {
        return YearMonth.parse(date).atDay(1);
    }
}
