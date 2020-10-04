package com.sensiblemetrics.api.customiere.validation.configuration;

import com.sensiblemetrics.api.customiere.validation.aspect.MethodParamsValidationAspect;
import com.sensiblemetrics.api.customiere.validation.model.DefaultValidatorRegistry;
import com.sensiblemetrics.api.customiere.validation.model.ValidatorRegistry;
import com.sensiblemetrics.api.customiere.validation.property.WebServiceValidationProperty;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.ValidatorFactory;
import java.util.List;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ConditionalOnProperty(prefix = WebServiceValidationProperty.PROPERTY_PREFIX, value = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(WebServiceValidationProperty.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebService Validation configuration")
public abstract class WebServiceValidationConfiguration {

    @Bean
    @ConditionalOnBean(ValidatorRegistry.class)
    @ConditionalOnProperty(prefix = WebServiceValidationProperty.Handlers.METHOD_PARAMS_PROPERTY_PREFIX, value = "enabled", havingValue = "true")
    @Description("Method params validation aspect bean")
    public MethodParamsValidationAspect methodParamsValidationAspect(final ValidatorRegistry validatorRegistry) {
        return new MethodParamsValidationAspect(validatorRegistry);
    }

    @Bean
    @ConditionalOnMissingBean
    @Description("Validator registry configuration bean")
    public ValidatorRegistry validatorRegistry(final ObjectProvider<List<Validator>> validatorProvider) {
        final DefaultValidatorRegistry registry = new DefaultValidatorRegistry();
        validatorProvider.ifAvailable(validators -> validators.forEach(registry::addValidator));
        return registry;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(MessageSource.class)
    @ConditionalOnClass(LocalValidatorFactoryBean.class)
    @Description("Validator factory configuration bean")
    public ValidatorFactory validator(final MessageSource validationSource) {
        final LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.setValidationMessageSource(validationSource);
        factoryBean.setParameterNameDiscoverer(new LocalVariableTableParameterNameDiscoverer());
        return factoryBean;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(ValidatorFactory.class)
    @Description("Method validation post processor configuration bean")
    public MethodValidationPostProcessor methodValidationPostProcessor(final ValidatorFactory validator) {
        final MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        processor.setValidatorFactory(validator);
        return processor;
    }
}
