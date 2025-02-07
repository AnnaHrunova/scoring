package org.inbank.scoring.external;

import org.springframework.stereotype.Service;

@Service
public class FinancialFactorProvider {

    public Integer getFinancialFactor(String personId) {
        var lastDigit = Character.getNumericValue(personId.charAt(personId.length() - 1));
        return switch (lastDigit) {
            case 1 -> 50;
            case 2 -> 200;
            case 3 -> 300;
            case 4 -> 400;
            case 5 -> 500;
            default -> 100;
        };
    }
}
