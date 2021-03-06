package com.sensiblemetrics.api.customiere.commons.handler;

import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.lang.NonNull;

/**
 * Default {@link AbstractFailureAnalyzer} implementation
 */
public class DefaultFailureAnalyzer extends AbstractFailureAnalyzer<BeanNotOfRequiredTypeException> {

    /**
     * Returns an analysis of the given {@code failure}, or {@code null} if no analysis
     * was possible.
     *
     * @param exception - initial input {@link Throwable} instance passed to the analyzer
     * @param cause     - initial input {@link BeanNotOfRequiredTypeException} cause
     * @return {@link FailureAnalysis} instance
     */
    @NonNull
    @Override
    protected FailureAnalysis analyze(final Throwable exception,
                                      final BeanNotOfRequiredTypeException cause) {
        return new FailureAnalysis(this.getDescription(cause), this.getAction(cause), cause);
    }

    /**
     * Returns failure description {@link String}
     *
     * @param cause - initial input {@link BeanNotOfRequiredTypeException} cause
     * @return failure description {@link String}
     */
    private String getDescription(final BeanNotOfRequiredTypeException cause) {
        return String.format(
                "The bean {%s} could not be injected as {%s} because it is of type {%s}",
                cause.getBeanName(),
                cause.getRequiredType().getName(),
                cause.getActualType().getName()
        );
    }

    /**
     * Returns failure action {@link String}
     *
     * @param cause - initial input {@link BeanNotOfRequiredTypeException} cause
     * @return failure action {@link String}
     */
    private String getAction(final BeanNotOfRequiredTypeException cause) {
        return String.format(
                "Consider creating a bean with name {%s} of type {%s}",
                cause.getBeanName(),
                cause.getRequiredType().getName()
        );
    }
}
