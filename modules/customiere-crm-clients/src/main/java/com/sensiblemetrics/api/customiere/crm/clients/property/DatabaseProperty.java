package com.sensiblemetrics.api.customiere.crm.clients.property;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
@Validated
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ConfigurationProperties(prefix = DatabaseProperty.PROPERTY_PREFIX, ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebService DataSource configuration properties")
public class DatabaseProperty extends DataSourceProperties {
    /**
     * Default datasource property prefix
     */
    public static final String PROPERTY_PREFIX = "spring.datasource";

    /**
     * Default database host
     */
    @NotBlank(message = "Database <host> property should not be blank")
    private String host;
    /**
     * Default database port
     */
    @PositiveOrZero(message = "Database <port> property should be positive or zero")
    private int port;
    /**
     * Default database name
     */
    @NotBlank(message = "Database <database> property should not be blank")
    private String database;
}
