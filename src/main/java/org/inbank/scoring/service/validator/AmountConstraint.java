package org.inbank.scoring.service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AmountValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AmountConstraint {
    String message() default "Invalid approvedAmount";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
