package org.inbank.scoring.external;

import org.apache.commons.lang3.tuple.Triple;
import org.inbank.scoring.api.PurchaseApprovalResult;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ScoreProvider {

    public PurchaseApprovalResult evaluate(int financialFactor, int amount, Integer term) {
        var approvalScore = calculateApprovalScore(financialFactor, amount, term);
        if (approvalScore.compareTo(BigDecimal.ONE) > 0) {
            var extendedAmount = calculateExtendedAmount(amount);
            var msg = String.format("Your requested amount=%s was approved. Your recommended amount is %s", amount, extendedAmount);
            return new PurchaseApprovalResult(amount, term, msg, approvalScore);
        }

        if (approvalScore.compareTo(new BigDecimal("0.5")) < 0) {
            var reducedAmount = calculateReducedAmount(amount);
            var msg = String.format("Your requested amount=%s was rejected. Your recommended amount is %s", amount, reducedAmount);
            return new PurchaseApprovalResult(amount, term, msg, approvalScore);
        } else {
            var extendedTerm = calculateExtendedTerm(term);
            var msg = String.format("Your requested amount=%s for term=%s was rejected. Your recommended term for requester amount is: %s", amount, term, extendedTerm);
            return new PurchaseApprovalResult(amount, term, msg, approvalScore);
        }
    }

    private BigDecimal calculateApprovalScore(int financialFactor, int amount, int term) {
        return BigDecimal.valueOf(financialFactor)
                .divide(BigDecimal.valueOf(amount), 3, RoundingMode.CEILING)
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
