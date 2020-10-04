package com.sensiblemetrics.api.customiere.logger.annotation;

import com.sensiblemetrics.api.customiere.logger.handler.LogApplicationEventListener;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(LogApplicationEventListener.class)
public @interface EnableWsEventLogging {
}
