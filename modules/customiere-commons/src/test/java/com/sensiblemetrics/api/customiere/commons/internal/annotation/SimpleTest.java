package com.sensiblemetrics.api.customiere.commons.internal.annotation;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableBootstrapTest
public @interface SimpleTest {
}
