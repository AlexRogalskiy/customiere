package com.sensiblemetrics.api.customiere.logger.annotation;

import com.sensiblemetrics.api.customiere.logger.configuration.WebServiceLoggingConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(WebServiceLoggingConfiguration.class)
public @interface EnableWsLogging {
}
