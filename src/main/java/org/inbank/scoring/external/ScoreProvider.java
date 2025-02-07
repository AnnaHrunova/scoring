package org.inbank.scoring.external;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ScoreProvider {

    public Triple<Integer, Integer, BigDecimal> evaluate(int financialFactor, int amount, Integer term) {
        var approvalScore = calculateApprovalScore(financialFactor, amount, term);
        if (approvalScore.compareTo(BigDecimal.ZERO) > 0) {
            return Triple.of(amount, term, approvalScore);
        }

        return Triple.of(0, 0, approvalScore);
    }

    private BigDecimal calculateApprovalScore(int financialFactor, int amount, Integer term) {
        return BigDecimal.valueOf(financialFactor)
                .divide(BigDecimal.valueOf(amount), 3, RoundingMode.CEILING)
                .multiply(BigDecimal.valueOf(term));
    }
}
