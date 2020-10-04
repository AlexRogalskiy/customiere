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
 * Invalid parameter {@link RuntimeException} implementation
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid parameter")
public class InvalidParameterException extends RuntimeException {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 8516167662034280227L;

    /**
     * {@link InvalidParameterException} constructor with initial input message
     *
     * @param message - initial input message {@link String}
     */
    public InvalidParameterException(final String message) {
        super(message);
    }

    /**
     * {@link InvalidParameterException} constructor with initial input target {@link Throwable}
     *
     * @param cause - initial input target {@link Throwable}
     */
    public InvalidParameterException(final Throwable cause) {
        super(cause);
    }

    /**
     * {@link InvalidParameterException} constructor with initial input {@link String} message and {@link Throwable} target
     *
     * @param message - initial input message {@link String}
     * @param cause   - initial input target {@link Throwable}
     */
    public InvalidParameterException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns {@link InvalidParameterException} by input parameters
     *
     * @param message - initial input description {@link String}
     * @return {@link InvalidParameterException}
     */
    @NonNull
    public static InvalidParameterException throwError(final String message) {
        throw new InvalidParameterException(message);
    }

    /**
     * Returns {@link InvalidParameterException} by input parameters
     *
     * @param args - initial input message arguments {@link Object}
     * @return {@link InvalidParameterException}
     */
    @NonNull
    public static InvalidParameterException throwInvalidParam(@Nullable final Object... args) {
        return throwInvalidParamWith(ErrorTemplateType.INVALID_PARAMETER.getErrorMessage(), args);
    }

    /**
     * Returns {@link InvalidParameterException} by input parameters
     *
     * @param messageId - initial input message {@link String} identifier
     * @param args      - initial input description {@link Object} arguments
     * @return {@link InvalidParameterException}
     */
    @NonNull
    public static InvalidParameterException throwInvalidParamWith(final String messageId,
                                                                  @Nullable final Object... args) {
        throw throwError(MessageSourceHelper.getMessage(messageId, args));
    }
}
