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
 * Resource already exist {@link RuntimeException} implementation
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Resource already exist")
public class ResourceAlreadyExistException extends RuntimeException {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 8516167662034280227L;

    /**
     * {@link ResourceAlreadyExistException} constructor with initial input message
     *
     * @param message - initial input message {@link String}
     */
    public ResourceAlreadyExistException(final String message) {
        super(message);
    }

    /**
     * {@link ResourceAlreadyExistException} constructor with initial input target {@link Throwable}
     *
     * @param cause - initial input target {@link Throwable}
     */
    public ResourceAlreadyExistException(final Throwable cause) {
        super(cause);
    }

    /**
     * {@link ResourceAlreadyExistException} constructor with initial input {@link String} message and {@link Throwable} target
     *
     * @param message - initial input message {@link String}
     * @param cause   - initial input target {@link Throwable}
     */
    public ResourceAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns {@link ResourceAlreadyExistException} by input parameters
     *
     * @param message - initial input description {@link String}
     * @return {@link ResourceAlreadyExistException}
     */
    @NonNull
    public static ResourceAlreadyExistException throwError(final String message) {
        throw new ResourceAlreadyExistException(message);
    }

    /**
     * Returns {@link ResourceAlreadyExistException} by input parameters
     *
     * @param args - initial input message arguments {@link Object}
     * @return {@link ResourceAlreadyExistException}
     */
    @NonNull
    public static ResourceAlreadyExistException throwResourceExist(@Nullable final Object... args) {
        return throwResourceExistWith(ErrorTemplateType.RESOURCE_ALREADY_EXIST.getErrorMessage(), args);
    }

    /**
     * Returns {@link ResourceAlreadyExistException} by input parameters
     *
     * @param args - initial input message arguments {@link Object}
     * @return {@link ResourceAlreadyExistException}
     */
    @NonNull
    public static ResourceAlreadyExistException throwResourcesExist(@Nullable final Object... args) {
        return throwResourceExistWith(ErrorTemplateType.RESOURCES_ALREADY_EXIST.getErrorMessage(), args);
    }

    /**
     * Returns {@link ResourceAlreadyExistException} by input parameters
     *
     * @param messageId - initial input message {@link String} identifier
     * @param args      - initial input description {@link Object} arguments
     * @return {@link ResourceAlreadyExistException}
     */
    @NonNull
    public static ResourceAlreadyExistException throwResourceExistWith(final String messageId,
                                                                       @Nullable final Object... args) {
        throw throwError(MessageSourceHelper.getMessage(messageId, args));
    }
}
