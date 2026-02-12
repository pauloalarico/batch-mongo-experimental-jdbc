package org.batch.experimental.application;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ClientFileDTO(
        String externalId,
        String name,
        String email,
        String productType,
        BigDecimal amount,
        boolean active,
        String creationDate) {
}
