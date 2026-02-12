package org.batch.experimental.infra.batch.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JdbcJobRepositoryFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties("spring.datasource")
public class DataSourceConfig {
    private String jdbcUrl;
    private String username;
    private String password;

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariConfig hikari =  new HikariConfig();
        hikari.setJdbcUrl(jdbcUrl);
        hikari.setUsername(username);
        hikari.setPassword(password);
        return new HikariDataSource(hikari);
    }

    @Bean
    @Primary
    public JobRepository jobRepository (DataSource dataSource,
                                            PlatformTransactionManager transactionManager) throws Exception {
        JdbcJobRepositoryFactoryBean factory = new JdbcJobRepositoryFactoryBean();
       factory.setDataSource(dataSource);
       factory.setTransactionManager(transactionManager);
       factory.setDatabaseType("POSTGRES");
        return factory.getObject();
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new JdbcTransactionManager(dataSource);
    }
}
