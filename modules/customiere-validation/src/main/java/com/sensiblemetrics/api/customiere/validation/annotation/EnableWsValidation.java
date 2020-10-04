package com.sensiblemetrics.api.customiere.validation.annotation;

import com.sensiblemetrics.api.customiere.validation.configuration.WebServiceValidationConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(WebServiceValidationConfiguration.class)
public @interface EnableWsValidation {
}
