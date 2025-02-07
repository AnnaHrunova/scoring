package org.inbank.scoring.service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class PersonIdValidator implements
        ConstraintValidator<PersonIdConstraint, String> {

    @Override
    public void initialize(PersonIdConstraint personId) {
    }

    @Override
    public boolean isValid(String personId,
                           ConstraintValidatorContext cxt) {

        if (isBlank(personId)) {
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate("Person id must not be blank!")
                    .addConstraintViolation();
            return false;
        }

        if (personId.length() < 10 || personId.length() > 12) {
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate("Person id length must be between 10 and 12 symbols!")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
