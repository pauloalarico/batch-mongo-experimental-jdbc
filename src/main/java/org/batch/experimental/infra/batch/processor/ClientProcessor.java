package org.batch.experimental.infra.batch.processor;

import lombok.RequiredArgsConstructor;
import org.batch.experimental.application.ClientFileDTO;
import org.batch.experimental.domain.entitie.Client;
import org.batch.experimental.infra.persistance.ClientMapper;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientProcessor implements ItemProcessor<ClientFileDTO, Client> {
    private final ClientMapper clientMapper;

    @Override
    public @Nullable Client process(ClientFileDTO item) throws Exception {
        return clientMapper.toDomain(item);
    }
}
