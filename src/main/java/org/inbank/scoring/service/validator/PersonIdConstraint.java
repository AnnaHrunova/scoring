package org.inbank.scoring.service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PersonIdValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PersonIdConstraint {
    String message() default "Invalid person id";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
