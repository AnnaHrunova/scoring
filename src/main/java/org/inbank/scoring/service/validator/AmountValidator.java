package org.inbank.scoring.service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AmountValidator implements
        ConstraintValidator<AmountConstraint, String> {

    @Override
    public void initialize(AmountConstraint amount) {
    }

    @Override
    public boolean isValid(String initAmount,
                           ConstraintValidatorContext cxt) {
        try {
            var amount = Integer.parseInt(initAmount);
            if (amount < 200 || amount > 5000) {
                cxt.disableDefaultConstraintViolation();
                cxt.buildConstraintViolationWithTemplate("Amount must be between 200 and 5000 EUR!")
                        .addConstraintViolation();
                return false;
            }
        } catch (Exception e) {
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate("Invalid format for amount!")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

}
