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
 * Invalid format {@link Exception} implementation
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid format")
public class InvalidFormatException extends RuntimeException {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 8516167662034280227L;

    /**
     * {@link InvalidFormatException} constructor with initial input message
     *
     * @param message - initial input message {@link String}
     */
    public InvalidFormatException(final String message) {
        super(message);
    }

    /**
     * {@link InvalidFormatException} constructor with initial input target {@link Throwable}
     *
     * @param cause - initial input target {@link Throwable}
     */
    public InvalidFormatException(final Throwable cause) {
        super(cause);
    }

    /**
     * {@link InvalidFormatException} constructor with initial input {@link String} message and {@link Throwable} target
     *
     * @param message - initial input message {@link String}
     * @param cause   - initial input target {@link Throwable}
     */
    public InvalidFormatException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns {@link InvalidFormatException} by input parameters
     *
     * @param message - initial input description {@link String}
     * @return {@link InvalidFormatException}
     */
    @NonNull
    public static InvalidFormatException throwError(final String message) {
        throw new InvalidFormatException(message);
    }

    /**
     * Returns {@link InvalidFormatException} by input parameters
     *
     * @param args - initial input message arguments {@link Object}
     * @return {@link InvalidFormatException}
     */
    @NonNull
    public static InvalidFormatException throwInvalidFormat(@Nullable final Object... args) {
        return throwInvalidFormatWith(ErrorTemplateType.INVALID_FORMAT.getErrorMessage(), args);
    }

    /**
     * Returns {@link InvalidFormatException} by input parameters
     *
     * @param messageId - initial input message {@link String} identifier
     * @param args      - initial input description {@link Object} arguments
     * @return {@link InvalidFormatException}
     */
    @NonNull
    public static InvalidFormatException throwInvalidFormatWith(final String messageId,
                                                                @Nullable final Object... args) {
        throw throwError(MessageSourceHelper.getMessage(messageId, args));
    }
}
