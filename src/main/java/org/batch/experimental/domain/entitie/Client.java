package org.batch.experimental.domain.entitie;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
public class Client {
    private String externalId;
    private String name;
    private String email;
    private String productType;
    private BigDecimal amount;
    private boolean active;
    private LocalDate creationDate;
}
