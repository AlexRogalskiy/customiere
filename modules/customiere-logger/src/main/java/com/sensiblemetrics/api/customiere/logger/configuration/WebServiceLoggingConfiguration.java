package com.sensiblemetrics.api.customiere.logger.configuration;

import com.sensiblemetrics.api.customiere.logger.aspect.ReportingDataAspect;
import com.sensiblemetrics.api.customiere.logger.handler.LogHeadersToMdcFilter;
import com.sensiblemetrics.api.customiere.logger.property.WebServiceLoggingProperty;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

@Configuration
@Import(ReportingDataAspect.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ConditionalOnProperty(prefix = WebServiceLoggingProperty.PROPERTY_PREFIX, value = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(WebServiceLoggingProperty.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Web Service Logging configuration")
public abstract class WebServiceLoggingConfiguration {

    @Bean
    @ConditionalOnBean(WebServiceLoggingProperty.class)
    @ConditionalOnClass(LogHeadersToMdcFilter.class)
    @ConditionalOnProperty(prefix = WebServiceLoggingProperty.Handlers.HEADERS_PROPERTY_PREFIX, name = "enabled", havingValue = "true")
    @Description("MDC filter headers logging configuration bean")
    public LogHeadersToMdcFilter logHeadersToMDCFilter(final WebServiceLoggingProperty property) {
        return new LogHeadersToMdcFilter(property.getHandlers().getHeaders());
    }
}
