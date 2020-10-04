package com.sensiblemetrics.api.customiere.crm.clients.property;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.sensiblemetrics.api.customiere.commons.property.PropertySettings.DEFAULT_PROPERTY_DELIMITER;
import static com.sensiblemetrics.api.customiere.commons.property.PropertySettings.DEFAULT_PROPERTY_PREFIX;

@Data
@Validated
@Accessors(chain = true)
@ConfigurationProperties(prefix = RestApiProperty.PROPERTY_PREFIX, ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebService Rest Api configuration properties")
public class RestApiProperty {
    /**
     * Default rest api property prefix
     */
    public static final String PROPERTY_PREFIX = DEFAULT_PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "rest-api";

    /**
     * Default logging handlers
     */
    @Valid
    @NestedConfigurationProperty
    @NotNull(message = "{property.rest-api.handlers.notNull}")
    private Handlers handlers = new Handlers();

    /**
     * Rest api handlers configuration properties
     */
    @Data
    @Validated
    @Accessors(chain = true)
    public static class Handlers {
        /**
         * Default handlers property prefix
         */
        public static final String PROPERTY_PREFIX = RestApiProperty.PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "handlers";
    }

    /**
     * Validation handler configuration properties
     */
    @Data
    @Validated
    @Accessors(chain = true)
    public static class Handler {
        /**
         * Enable/disable handler ({@code true} by default)
         */
        private boolean enabled = true;
    }

    /**
     * Enable/disable rest api configuration ({@code true} by default)
     */
    private boolean enabled = true;
}
