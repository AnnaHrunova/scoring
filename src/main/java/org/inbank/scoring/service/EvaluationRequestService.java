package org.inbank.scoring.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.inbank.scoring.api.PurchaseApprovalResult;
import org.inbank.scoring.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EvaluationRequestService {

    private final CustomerRepository customerRepository;
    private final ScoringProfileRepository scoringProfileRepository;
    private final EvaluationRequestRepository evaluationRequestRepository;

    @Transactional
    public UUID prepareCustomer(String personId, Integer financialFactor) {
        var scoringProfile = new ScoringProfile();
        scoringProfile.setFinancialCapacity(financialFactor);
        var savedScoringProfile = scoringProfileRepository.save(scoringProfile);

        var customer = new Customer();
        customer.setPersonId(personId);
        customer.setScoringProfile(savedScoringProfile);

        return customerRepository.save(customer).getId();
    }

    public Optional<Integer> getCustomerFinancialFactor(String personId) {
        return customerRepository.findByPersonId(personId)
                .map(Customer::getScoringProfile)
                .filter(p -> !p.isIneligible())
                .map(ScoringProfile::getFinancialCapacity);
    }

    public boolean isCustomerIneligible(String personId) {
        return customerRepository.findByPersonId(personId)
                .map(Customer::getScoringProfile)
                .stream()
                .anyMatch(ScoringProfile::isIneligible);
    }

    @Transactional
    public UUID savePurchaseApprovalRequest(String personId, int requestedAmount, Integer requestedTerm) {
        var customer = customerRepository.findByPersonId(personId).orElseThrow();
        var request = new EvaluationRequest();
        request.setRequestedAmount(requestedAmount);
        request.setRequestedTerm(requestedTerm);
        request.setCustomer(customer);
        return evaluationRequestRepository.save(request).getId();
    }

    @Transactional
    public void finishPurchaseApprovalRequest(UUID requestId, PurchaseApprovalResult evaluationResult) {
        var request = evaluationRequestRepository.findById(requestId).orElseThrow();
        request.setApprovedAmount(evaluationResult.approvedAmount());
        request.setApprovedTerm(evaluationResult.approvedTerm());
        request.setApprovalScore(evaluationResult.approvalScore());
        evaluationRequestRepository.save(request);
    }

}
