package com.sensiblemetrics.api.customiere.crm.clients;

import com.sensiblemetrics.api.customiere.actuator.annotation.EnableApiStatus;
import com.sensiblemetrics.api.customiere.actuator.annotation.EnableWsActuatorSecurity;
import com.sensiblemetrics.api.customiere.executor.annotation.EnableWsExecutor;
import com.sensiblemetrics.api.customiere.logger.annotation.EnableWsEventLogging;
import com.sensiblemetrics.api.customiere.logger.annotation.EnableWsLogging;
import com.sensiblemetrics.api.customiere.metrics.annotation.EnableWsMetrics;
import com.sensiblemetrics.api.customiere.security.annotation.EnableWsEncryptableProperties;
import com.sensiblemetrics.api.customiere.validation.annotation.EnableWsValidation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableCaching
@EnableWsExecutor
@EnableWsMetrics
@EnableApiStatus
@EnableWsLogging
@EnableWsValidation
@EnableWsEventLogging
@EnableWsActuatorSecurity
@EnableWsEncryptableProperties
@EnableConfigurationProperties
@SpringBootApplication
public class ApplicationBootstrap {

    public static void main(final String[] args) {
        SpringApplication.run(ApplicationBootstrap.class, args);
    }
}
