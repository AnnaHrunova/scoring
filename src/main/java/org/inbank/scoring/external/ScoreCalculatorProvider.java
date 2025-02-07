package org.inbank.scoring.external;

import org.inbank.scoring.api.PurchaseApprovalResult;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ScoreCalculatorProvider {

    public PurchaseApprovalResult evaluate(int financialFactor, int amount, Integer term) {
        var approvalScore = calculateApprovalScore(financialFactor, amount, term);

        if (approvalScore.compareTo(BigDecimal.ONE) == 0) {
            var msg = String.format("Your requested amount=%s was approved.", amount);
            return new PurchaseApprovalResult(amount, term, msg, approvalScore, false);
        }

        if (approvalScore.compareTo(BigDecimal.ONE) >= 0) {
            var extendedAmount = calculateExtendedAmount(amount);
            var msg = String.format("Your requested amount=%s was approved. Your recommended amount is %s.", amount, extendedAmount);
            return new PurchaseApprovalResult(extendedAmount, term, msg, approvalScore, false);
        }

        var reducedAmount = calculateReducedAmount(amount);
        if (approvalScore.compareTo(new BigDecimal("0.5")) >= 0) {
            var msg = String.format("Your requested amount=%s was rejected. Your recommended amount is %s.", amount, reducedAmount);
            return new PurchaseApprovalResult(reducedAmount, term, msg, approvalScore, false);
        } else {
            var extendedTerm = calculateExtendedTerm(term);
            var msg = String.format("Your requested amount=%s for term=%s was rejected. Your recommended amount is %s and term is: %s.", amount, term, reducedAmount, extendedTerm);
            return new PurchaseApprovalResult(reducedAmount, extendedTerm, msg, approvalScore, false);
        }
    }

    private BigDecimal calculateApprovalScore(int financialFactor, int amount, int term) {
        return BigDecimal.valueOf(financialFactor)
                .divide(BigDecimal.valueOf(amount), 2, RoundingMode.CEILING)
                .multiply(BigDecimal.valueOf(term));
    }

    private Integer calculateExtendedTerm(int term) {
        return term + 5;
    }

    private Integer calculateExtendedAmount(int amount) {
        return amount + 100;
    }

    private Integer calculateReducedAmount(int amount) {
        return amount - 100;
    }
}
