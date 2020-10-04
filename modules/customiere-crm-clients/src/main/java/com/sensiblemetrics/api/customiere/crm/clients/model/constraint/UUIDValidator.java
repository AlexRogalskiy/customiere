package com.sensiblemetrics.api.customiere.crm.clients.model.constraint;

import org.springframework.util.Assert;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;
import java.util.function.Predicate;

import static com.sensiblemetrics.api.customiere.crm.clients.model.utils.ConstraintUtils.validate;

/**
 * {@link UUID} {@link ConstraintValidator} implementation
 */
public class UUIDValidator implements ConstraintValidator<UUID, String> {

    /**
     * Default uuid pattern
     */
    public static final String DEFAULT_UUID_PATTERN = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";

    /**
     * Default {@link String} message
     */
    private String message;

    /**
     * Default {@link UUID} {@link Predicate}
     */
    private final Predicate<String> uuidPredicate = value -> value.matches(DEFAULT_UUID_PATTERN);

    @Override
    public void initialize(final UUID constraintAnnotation) {
        Assert.notNull(constraintAnnotation, "Constraint annotation should not be null");
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final String uuid, final ConstraintValidatorContext context) {
        return Optional.ofNullable(uuid)
            .map(value -> validate(this.uuidPredicate, value, this.message, context))
            .orElse(true);
    }
}
