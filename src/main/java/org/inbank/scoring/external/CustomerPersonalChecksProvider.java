package org.inbank.scoring.external;

import org.springframework.stereotype.Service;

@Service
public class CustomerPersonalChecksProvider {

    public boolean isIneligible(String personId) {
        var lastDigit = Character.getNumericValue(personId.charAt(personId.length() - 1));
        return lastDigit == 0;
    }
}
