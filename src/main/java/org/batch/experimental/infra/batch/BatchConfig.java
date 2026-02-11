package org.batch.experimental.infra.batch;

import lombok.RequiredArgsConstructor;
import org.batch.experimental.domain.entitie.Client;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.data.MongoItemWriter;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {
    @Value("${apps.source.file-path}")
    private String path;

    private final MongoTemplate mongoTemplate;
    @Value("${apps.source.collection-mongo}")
    private String collection;

    @Bean
    public Job jobBuilder(JobRepository jobRepository, Step initialStep) {
        return new JobBuilder("mongo-persist", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(initialStep)
                .build();
    }

    @Bean
    public Step initialStep(JobRepository jobRepository,
                            PlatformTransactionManager transactionManager,
                            ItemReader<Client> reader,
                            ItemWriter<Client> writer) {
        return new StepBuilder("initial-step", jobRepository)
                .<Client,Client>chunk(5)
                .transactionManager(transactionManager)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<Client> itemReader() {
        return new FlatFileItemReaderBuilder<Client>()
                .name("reader-file")
                .linesToSkip(1)
                .resource(new FileSystemResource(path))
                .delimited()
                .delimiter(",")
                .names("externalId", "name", "email", "productType", "amount", "active", "creationDate")
                .targetType(Client.class)
                .build();
    }

    @Bean
    public ItemWriter<Client> itemWriter() {
        MongoItemWriter<Client> writer = new MongoItemWriter<>(mongoTemplate);
        writer.setCollection(collection);
        return writer;
    }
}
