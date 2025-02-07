package org.inbank.scoring.service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class TermValidator implements
        ConstraintValidator<TermConstraint, String> {

    @Override
    public void initialize(TermConstraint term) {
    }

    @Override
    public boolean isValid(String initTerm,
                           ConstraintValidatorContext cxt) {

        if (isBlank(initTerm)) {
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate("Term must not be blank!")
                    .addConstraintViolation();
            return false;
        }

        try {
            var term = Integer.parseInt(initTerm);
            if (term < 6 || term > 24) {
                cxt.disableDefaultConstraintViolation();
                cxt.buildConstraintViolationWithTemplate("Term must be between 6 and 12 months!")
                        .addConstraintViolation();
                return false;
            }
        } catch (Exception e) {
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate("Invalid format for term!")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

}
