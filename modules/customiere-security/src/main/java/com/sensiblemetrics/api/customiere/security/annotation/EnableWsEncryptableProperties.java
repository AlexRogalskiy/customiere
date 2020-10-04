package com.sensiblemetrics.api.customiere.security.annotation;

import com.sensiblemetrics.api.customiere.security.configuration.WebServiceSecurityEncryptablePropertiesConfiguration;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@EnableEncryptableProperties
@Import(WebServiceSecurityEncryptablePropertiesConfiguration.class)
public @interface EnableWsEncryptableProperties {
}
