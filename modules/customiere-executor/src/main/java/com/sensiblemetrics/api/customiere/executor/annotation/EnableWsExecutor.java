package com.sensiblemetrics.api.customiere.executor.annotation;

import com.sensiblemetrics.api.customiere.executor.configuration.WebServiceExecutorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(WebServiceExecutorConfiguration.class)
public @interface EnableWsExecutor {
}
