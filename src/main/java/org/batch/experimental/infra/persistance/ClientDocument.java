package org.batch.experimental.infra.persistance;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Document("${apps.source.collection-mongo}")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ClientDocument {
    private String id;
    @Indexed(unique = true)
    private String externalId;
    private String name;
    private String email;
    private String productType;
    private BigDecimal amount;
    private boolean active;
    private LocalDate creationDate;
}
