package com.sensiblemetrics.api.customiere.security.configuration;

import com.sensiblemetrics.api.customiere.security.handler.DelegatedEncryptablePropertyDetector;
import com.sensiblemetrics.api.customiere.security.handler.DelegatedEncryptablePropertyFilter;
import com.sensiblemetrics.api.customiere.security.handler.DelegatedEncryptablePropertyResolver;
import com.sensiblemetrics.api.customiere.security.property.WebServiceEncryptableProperty;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyFilter;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;

@Configuration
@EnableConfigurationProperties(WebServiceEncryptableProperty.class)
@ConditionalOnProperty(prefix = WebServiceEncryptableProperty.PROPERTY_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Web Service Security Encryptable properties configuration")
public abstract class WebServiceSecurityEncryptablePropertiesConfiguration {
    /**
     * Default encryptable bean naming conventions
     */
    public static final String JASYPT_STRING_ENCRYPTOR_BEAN_NAME = "jasyptStringEncryptor";
    public static final String ENCRYPTABLE_PROPERTY_DETECTOR_BEAN_NAME = "encryptablePropertyDetector";
    public static final String ENCRYPTABLE_PROPERTY_RESOLVER_BEAN_NAME = "encryptablePropertyResolver";
    public static final String ENCRYPTABLE_PROPERTY_FILTER_BEAN_NAME = "encryptablePropertyFilter";

    @Bean(JASYPT_STRING_ENCRYPTOR_BEAN_NAME)
    @ConditionalOnMissingBean(name = JASYPT_STRING_ENCRYPTOR_BEAN_NAME)
    @ConditionalOnBean(SimpleStringPBEConfig.class)
    @Description("Security string encryptor bean")
    public StringEncryptor stringEncryptor(final SimpleStringPBEConfig config) {
        final PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(config);
        return encryptor;
    }

    @Bean(ENCRYPTABLE_PROPERTY_DETECTOR_BEAN_NAME)
    @ConditionalOnMissingBean(name = ENCRYPTABLE_PROPERTY_DETECTOR_BEAN_NAME)
    @ConditionalOnBean(WebServiceEncryptableProperty.class)
    @ConditionalOnClass(DelegatedEncryptablePropertyDetector.class)
    @Description("Security encryptable property detector bean")
    public EncryptablePropertyDetector encryptablePropertyDetector(final WebServiceEncryptableProperty property) {
        return new DelegatedEncryptablePropertyDetector(property.getEncryptedPrefix());
    }

    @Bean(ENCRYPTABLE_PROPERTY_RESOLVER_BEAN_NAME)
    @ConditionalOnMissingBean(name = ENCRYPTABLE_PROPERTY_RESOLVER_BEAN_NAME)
    @ConditionalOnBean({WebServiceEncryptableProperty.class, SimpleStringPBEConfig.class})
    @ConditionalOnClass(DelegatedEncryptablePropertyResolver.class)
    @Description("Security encryptable property resolver bean")
    public EncryptablePropertyResolver encryptablePropertyResolver(final WebServiceEncryptableProperty property,
                                                                   final SimpleStringPBEConfig config) {
        return new DelegatedEncryptablePropertyResolver(property.getEncryptedPrefix(), config);
    }

    @Bean(ENCRYPTABLE_PROPERTY_FILTER_BEAN_NAME)
    @ConditionalOnMissingBean(name = ENCRYPTABLE_PROPERTY_FILTER_BEAN_NAME)
    @ConditionalOnBean(WebServiceEncryptableProperty.class)
    @ConditionalOnClass(DelegatedEncryptablePropertyFilter.class)
    @Description("Security encryptable property filter bean")
    public EncryptablePropertyFilter encryptablePropertyFilter(final WebServiceEncryptableProperty property) {
        return new DelegatedEncryptablePropertyFilter(property.getEncryptedMarker());
    }
}
