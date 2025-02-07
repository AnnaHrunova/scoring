package org.inbank.scoring.service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TermValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TermConstraint {
    String message() default "Invalid approvedTerm";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
