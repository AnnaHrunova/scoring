package org.inbank.scoring.service;

import org.inbank.scoring.api.PurchaseApprovalResult;
import org.inbank.scoring.config.BaseIntegrationTest;
import org.inbank.scoring.domain.CustomerRepository;
import org.inbank.scoring.domain.EvaluationRequestRepository;
import org.inbank.scoring.domain.ScoringProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EvaluationRequestServiceTest extends BaseIntegrationTest {

    @Autowired
    private EvaluationRequestService subject;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ScoringProfileRepository scoringProfileRepository;

    @Autowired
    private EvaluationRequestRepository evaluationRequestRepository;

    @Test
    void shouldInitializeCustomerWithScoringProfile() {
        var customerPersonId = "112233-56789";
        var newCustomer = subject.prepareCustomer(customerPersonId, 100);

        var savedCustomer = customerRepository.findById(newCustomer);
        assertTrue(savedCustomer.isPresent());

        var savedScoringProfile = savedCustomer.get().getScoringProfile();
        assertEquals(100, savedScoringProfile.getFinancialCapacity());
    }

    @Test
    void shouldSavePurchaseApprovalRequest() {
        var customerPersonId = "112233-56788";
        subject.prepareCustomer(customerPersonId, 100);

        var initRequestSize = evaluationRequestRepository.findAllByCustomerPersonId(customerPersonId).size();
        assertEquals(0, initRequestSize);

        var requestId = subject.savePurchaseApprovalRequest(customerPersonId, 500, 10);
        var currentRequestSize = evaluationRequestRepository.findAllByCustomerPersonId(customerPersonId).size();
        assertEquals(1, currentRequestSize);

        var request = evaluationRequestRepository.findById(requestId);
        assertTrue(request.isPresent());
        assertEquals(500, request.get().getRequestedAmount());
        assertEquals(10, request.get().getRequestedTerm());

        var result = new PurchaseApprovalResult(400, 9, "ok", new BigDecimal("0.5"), false);
        subject.finishPurchaseApprovalRequest(requestId, result);

        request = evaluationRequestRepository.findById(requestId);
        assertTrue(request.isPresent());
        assertEquals(500, request.get().getRequestedAmount());
        assertEquals(10, request.get().getRequestedTerm());
        assertEquals(400, request.get().getApprovedAmount());
        assertEquals(9, request.get().getApprovedTerm());
        assertEquals(new BigDecimal("0.50"), request.get().getApprovalScore());
    }

}