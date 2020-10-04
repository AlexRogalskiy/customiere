package com.sensiblemetrics.api.customiere.actuator.annotation;

import com.sensiblemetrics.api.customiere.actuator.configuration.WebServiceActuatorSecurityConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(WebServiceActuatorSecurityConfiguration.class)
public @interface EnableWsActuatorSecurity {
}
