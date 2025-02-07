package org.inbank.scoring.external;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreCalculatorProviderTest {

    @Test
    void test() {
        ScoreCalculatorProvider provider = new ScoreCalculatorProvider();

        var approveResult = provider.evaluate(50, 500, 10);
        assertEquals("Your requested amount=500 was approved.", approveResult.message());
        assertEquals(500, approveResult.approvedAmount());
        assertEquals(10, approveResult.approvedTerm());
        assertEquals(new BigDecimal("1.00"), approveResult.approvalScore());

        var approveRecommendedResult = provider.evaluate(100, 500, 10);
        assertEquals("Your requested amount=500 was approved. Your recommended amount is 600.", approveRecommendedResult.message());
        assertEquals(600, approveRecommendedResult.approvedAmount());
        assertEquals(10, approveRecommendedResult.approvedTerm());
        assertEquals(new BigDecimal("2.00"), approveRecommendedResult.approvalScore());


        var declineAmountResult = provider.evaluate(100, 2000, 10);
        assertEquals("Your requested amount=2000 was rejected. Your recommended amount is 1900.", declineAmountResult.message());
        assertEquals(1900, declineAmountResult.approvedAmount());
        assertEquals(10, declineAmountResult.approvedTerm());
        assertEquals(new BigDecimal("0.50"), declineAmountResult.approvalScore());

        var declineAmountTermResult = provider.evaluate(50, 5000, 10);
        assertEquals("Your requested amount=5000 for term=10 was rejected. Your recommended amount is 4900 and term is: 15.", declineAmountTermResult.message());
        assertEquals(4900, declineAmountTermResult.approvedAmount());
        assertEquals(15, declineAmountTermResult.approvedTerm());
        assertEquals(new BigDecimal("0.10"), declineAmountTermResult.approvalScore());

    }


}