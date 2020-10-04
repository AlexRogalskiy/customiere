package com.sensiblemetrics.api.customiere.commons.exception;

import com.sensiblemetrics.api.customiere.commons.enumeration.ErrorTemplateType;
import com.sensiblemetrics.api.customiere.commons.helper.MessageSourceHelper;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Empty content {@link Exception} implementation
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Empty content")
public class EmptyContentException extends RuntimeException {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 8516167662034280227L;

    /**
     * {@link EmptyContentException} constructor with initial input message
     *
     * @param message - initial input message {@link String}
     */
    public EmptyContentException(final String message) {
        super(message);
    }

    /**
     * {@link EmptyContentException} constructor with initial input target {@link Throwable}
     *
     * @param cause - initial input target {@link Throwable}
     */
    public EmptyContentException(final Throwable cause) {
        super(cause);
    }

    /**
     * {@link EmptyContentException} constructor with initial input {@link String} message and {@link Throwable} target
     *
     * @param message - initial input message {@link String}
     * @param cause   - initial input target {@link Throwable}
     */
    public EmptyContentException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns {@link EmptyContentException} by input parameters
     *
     * @param message - initial input description {@link String}
     * @return {@link EmptyContentException}
     */
    @NonNull
    public static EmptyContentException throwError(final String message) {
        throw new EmptyContentException(message);
    }

    /**
     * Returns {@link EmptyContentException} by input parameters
     *
     * @param args - initial input message arguments {@link Object}
     * @return {@link EmptyContentException}
     */
    @NonNull
    public static EmptyContentException throwEmptyContent(@Nullable final Object... args) {
        return throwEmptyContentWith(ErrorTemplateType.EMPTY_CONTENT.getErrorMessage(), args);
    }

    /**
     * Returns {@link EmptyContentException} by input parameters
     *
     * @param messageId - initial input message {@link String} identifier
     * @param args      - initial input description {@link Object} arguments
     * @return {@link EmptyContentException}
     */
    @NonNull
    public static EmptyContentException throwEmptyContentWith(final String messageId,
                                                              @Nullable final Object... args) {
        throw throwError(MessageSourceHelper.getMessage(messageId, args));
    }
}
