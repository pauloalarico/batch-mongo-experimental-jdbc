package org.batch.experimental.domain.entitie;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public class Client {
    @Id
    private String id;
    private String externalId;
    private String name;
    private String email;
    private String productType;
    private String amount;
    private String active;
    private LocalDate creationDate;
}
