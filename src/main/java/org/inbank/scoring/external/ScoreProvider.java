package org.inbank.scoring.external;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.tuple.Triple;
import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ScoreProvider {

    public Triple<BigDecimal, Integer, BigDecimal> evaluate(int financialFactor, BigDecimal amount, Integer term) {
        var approvalScore = calculateApprovalScore(financialFactor, amount, term);
        if (approvalScore.compareTo(BigDecimal.ZERO) > 0) {
            return Triple.of(amount, term, approvalScore);
        }

        return Triple.of(BigDecimal.ZERO, 0, approvalScore);
    }

    private BigDecimal calculateApprovalScore(int financialFactor, BigDecimal amount, Integer term) {
        return BigDecimal.valueOf(financialFactor)
                .divide(amount, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(term));
    }
}
