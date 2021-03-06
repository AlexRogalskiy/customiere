package com.sensiblemetrics.api.customiere.actuator.enumeration;

import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Application state {@link Enum} type
 */
public enum ApplicationStateType {
    /**
     * Application is up and running
     */
    OK,
    /**
     * Application is not running or suspended
     */
    DOWN;

    /**
     * Returns {@link List} of all {@link ApplicationStateType}s
     *
     * @return {@link List} of all {@link ApplicationStateType}s
     */
    @NonNull
    public static List<ApplicationStateType> valuesList() {
        return Collections.unmodifiableList(asList(ApplicationStateType.values()));
    }
}
