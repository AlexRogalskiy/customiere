package com.sensiblemetrics.api.customiere.crm.clients.configuration;

import com.sensiblemetrics.api.customiere.crm.clients.property.DatabaseProperty;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.core.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.core.ReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.dialect.PostgresDialect;
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactory;
import org.springframework.data.r2dbc.support.R2dbcExceptionTranslator;
import org.springframework.data.r2dbc.support.SqlStateR2dbcExceptionTranslator;

@Configuration
@EnableConfigurationProperties(DatabaseProperty.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebService DAO configuration")
public class WebServiceDaoConfiguration {

    @Bean
    public SensorRepository sensorRepository(final R2dbcRepositoryFactory factory) {
        return factory.getRepository(SensorRepository.class);
    }

    @Bean
    public SensorMetricsRepository sensorMetricsRepository(final R2dbcRepositoryFactory factory) {
        return factory.getRepository(SensorMetricsRepository.class);
    }

    @Bean
    public R2dbcRepositoryFactory factory(final DatabaseClient databaseClient,
                                          final ReactiveDataAccessStrategy dataAccessStrategy) {
        return new R2dbcRepositoryFactory(databaseClient, dataAccessStrategy);
    }

    @Bean
    public ReactiveDataAccessStrategy dataAccessStrategy() {
        return new DefaultReactiveDataAccessStrategy(PostgresDialect.INSTANCE);
    }

    @Bean
    public DatabaseClient databaseClient(final ConnectionFactory postgresConnectionFactory,
                                         final R2dbcExceptionTranslator exceptionTranslator) {
        return DatabaseClient.builder()
            .connectionFactory(postgresConnectionFactory)
            .exceptionTranslator(exceptionTranslator)
            .build();
    }

    @Bean
    public R2dbcExceptionTranslator exceptionTranslator() {
        return new SqlStateR2dbcExceptionTranslator();
    }

    @Bean
    public PostgresqlConnectionConfiguration connectionConfiguration(final DatabaseProperty property) {
        return PostgresqlConnectionConfiguration.builder()
            .host(property.getHost())
            .port(property.getPort())
            .database(property.getDatabase())
            .username(property.getUsername())
            .password(property.getPassword())
            .build();
    }

    @Bean
    public PostgresqlConnectionFactory postgresConnectionFactory(final PostgresqlConnectionConfiguration connectionConfiguration) {
        return new PostgresqlConnectionFactory(connectionConfiguration);
    }
}
