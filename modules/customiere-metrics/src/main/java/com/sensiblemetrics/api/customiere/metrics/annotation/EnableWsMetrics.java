package com.sensiblemetrics.api.customiere.metrics.annotation;

import com.sensiblemetrics.api.customiere.metrics.configuration.WebServiceMetricsConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Metrics configurator annotation
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(WebServiceMetricsConfiguration.class)
public @interface EnableWsMetrics {
}
