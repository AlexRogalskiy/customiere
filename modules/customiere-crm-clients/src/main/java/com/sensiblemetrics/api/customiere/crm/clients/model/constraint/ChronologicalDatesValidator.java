package com.sensiblemetrics.api.customiere.crm.clients.model.constraint;

import com.sensiblemetrics.api.customiere.crm.clients.model.entity.AuditEntity;
import com.sensiblemetrics.api.customiere.crm.clients.model.utils.ConstraintUtils;
import org.springframework.util.Assert;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * {@link ChronologicalDates} {@link ConstraintValidator} implementation
 */
public class ChronologicalDatesValidator implements ConstraintValidator<ChronologicalDates, AuditEntity<?>> {

    /**
     * Default {@link String} message
     */
    private String message;

    /**
     * Default {@link ChronologicalDates} datetime existence {@link Predicate}
     */
    private final Predicate<AuditEntity<?>> datesExistPredicate =
            value -> !(value.getCreatedDate().isPresent() && value.getLastModifiedDate().isPresent());

    /**
     * Default {@link ChronologicalDates} datetime order {@link Predicate}
     */
    private final Predicate<AuditEntity<?>> datesOrderPredicate =
            value -> value.getCreatedDate().map(Instant::toEpochMilli).orElse(0L)
                    <= value.getLastModifiedDate().map(Instant::toEpochMilli).orElse(0L);

    /**
     * Initializes validator by input {@link ChronologicalDates} annotation parameters
     *
     * @param constraintAnnotation - initial input {@link ChronologicalDates} annotation
     */
    @Override
    public void initialize(final ChronologicalDates constraintAnnotation) {
        Assert.notNull(constraintAnnotation, "Constraint annotation should not be null");
        this.message = constraintAnnotation.message();
    }

    /**
     * Validates input {@link AuditEntity} value by {@link ConstraintValidatorContext}
     *
     * @param auditEntity - initial input {@link AuditEntity} to validate
     * @param context     - initial input {@link ConstraintValidatorContext}
     * @return true - if input {@link AuditEntity} is valid, false - otherwise
     */
    @Override
    public boolean isValid(final AuditEntity<?> auditEntity,
                           final ConstraintValidatorContext context) {
        return Optional.ofNullable(auditEntity)
                .map(value -> ConstraintUtils.validate(this.datesExistPredicate, value, this.message, context)
                        || ConstraintUtils.validate(this.datesOrderPredicate, value, this.message, context))
                .orElse(true);
    }
}
