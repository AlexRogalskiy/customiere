package com.sensiblemetrics.api.customiere.security.property;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

import static com.sensiblemetrics.api.customiere.commons.property.PropertySettings.DEFAULT_PROPERTY_DELIMITER;

@Data
@Validated
@Accessors(chain = true)
@ConfigurationProperties(prefix = WebServiceEncryptableProperty.PROPERTY_PREFIX, ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Web Service Security Encryptable configuration properties")
public class WebServiceEncryptableProperty {
    /**
     * Default encryptable property prefix
     */
    public static final String PROPERTY_PREFIX = WebServiceSecurityProperty.PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "property";

    /**
     * Default encryption prefix
     */
    @NotBlank(message = "{property.security.property.encrypted-prefix.notBlank}")
    private String encryptedPrefix = "ENC@";

    /**
     * Default encryption marker
     */
    @NotBlank(message = "{property.security.property.encrypted-marker.notBlank}")
    private String encryptedMarker = "encrypted.";

    /**
     * Enable/disable encryptable configuration ({@code true} by default)
     */
    private boolean enabled = true;
}
