package org.batch.experimental.infra.batch.config;

import lombok.RequiredArgsConstructor;
import org.batch.experimental.application.ClientFileDTO;
import org.batch.experimental.domain.entitie.Client;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {
    @Value("${apps.source.file-path}")
    private String path;

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
                            ItemReader<ClientFileDTO> reader,
                            ItemWriter<Client> writer,
                            ItemProcessor<ClientFileDTO, Client> processor) {
        return new StepBuilder("initial-step", jobRepository)
                .<ClientFileDTO,Client>chunk(5)
                .transactionManager(transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<ClientFileDTO> itemReader() {
            return new FlatFileItemReaderBuilder<ClientFileDTO>()
                .name("reader-file")
                .linesToSkip(1)
                .resource(new FileSystemResource(path))
                .delimited()
                .delimiter(",")
                .names("externalId", "name", "email", "productType", "amount", "active", "creationDate")
                .targetType(ClientFileDTO.class)
                .build();
    }
}
