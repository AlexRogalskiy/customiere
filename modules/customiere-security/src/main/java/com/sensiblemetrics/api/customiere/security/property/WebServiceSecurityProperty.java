package com.sensiblemetrics.api.customiere.security.property;

import com.sensiblemetrics.api.customiere.validation.constraint.NullOrNotEmpty;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

import static com.sensiblemetrics.api.customiere.commons.property.PropertySettings.DEFAULT_PROPERTY_DELIMITER;
import static com.sensiblemetrics.api.customiere.commons.property.PropertySettings.DEFAULT_PROPERTY_PREFIX;

@Data
@Validated
@Accessors(chain = true)
@ConfigurationProperties(prefix = WebServiceSecurityProperty.PROPERTY_PREFIX, ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Web Service Security configuration properties")
public class WebServiceSecurityProperty {
    /**
     * Default security property prefix
     */
    public static final String PROPERTY_PREFIX = DEFAULT_PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "security";

    /**
     * Default {@link Map} collection of {@link Endpoint}s
     */
    @Valid
    @NullOrNotEmpty(message = "{property.security.endpoints.nullOrNotEmpty}")
    private Map<@NotBlank String, @NotNull Endpoint> endpoints;

    /**
     * Actuator security endpoint configuration properties
     */
    @Data
    @Builder
    @Validated
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Endpoint {
        @Valid
        @Singular
        @NotEmpty(message = "{property.security.endpoint.names.notEmpty}")
        private List<@NotBlank String> names;

        @Valid
        @Singular
        @NotEmpty(message = "{property.security.endpoint.roles.notEmpty}")
        private List<@NotBlank String> roles;

        /**
         * Enable security endpoint ({@code true} by default)
         */
        @Builder.Default
        private boolean enabled = true;
    }

    /**
     * Enable/disable security configuration ({@code true} by default)
     */
    private boolean enabled = true;
}
