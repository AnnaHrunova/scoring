package org.inbank.scoring.external;

import org.springframework.stereotype.Service;

/* For mocking customer personal checks */

@Service
public class CustomerPersonalChecksProvider {

    public boolean isIneligible(String personId) {
        var lastDigit = Character.getNumericValue(personId.charAt(personId.length() - 1));
        return lastDigit == 0;
    }
}
