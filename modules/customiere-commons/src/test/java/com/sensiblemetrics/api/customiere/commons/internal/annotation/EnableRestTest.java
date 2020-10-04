package com.sensiblemetrics.api.customiere.commons.internal.annotation;

import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@AutoConfigureCache
@AutoConfigureWebMvc
@AutoConfigureMockMvc
public @interface EnableRestTest {
}
