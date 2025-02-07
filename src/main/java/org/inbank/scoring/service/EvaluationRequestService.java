package org.inbank.scoring.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.inbank.scoring.api.PurchaseApprovalResponse;
import org.inbank.scoring.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EvaluationRequestService {

    private final CustomerRepository customerRepository;
    private final ScoringProfileRepository scoringProfileRepository;
    private final EvaluationRequestRepository evaluationRequestRepository;

    @Transactional
    public UUID prepareCustomer(String personalId, Integer financialFactor) {
        var scoringProfile = new ScoringProfile();
        scoringProfile.setFinancialCapacity(financialFactor);
        var savedScoringProfile = scoringProfileRepository.save(scoringProfile);

        var customer = new Customer();
        customer.setPersonalId(personalId);
        customer.setScoringProfile(savedScoringProfile);

        return customerRepository.save(customer).getId();
    }

    public Optional<Integer> getCustomerFinancialFactor(String personalId) {
        return customerRepository.findByPersonalId(personalId)
                .map(Customer::getScoringProfile)
                .filter(p -> !p.isIneligible())
                .map(ScoringProfile::getFinancialCapacity);
    }

    public boolean isCustomerIneligible(String personalId) {
        return customerRepository.findByPersonalId(personalId)
                .map(Customer::getScoringProfile)
                .stream()
                .anyMatch(ScoringProfile::isIneligible);
    }

    @Transactional
    public UUID savePurchaseApprovalRequest(String personalId, BigDecimal requestedAmount, Integer requestedTerm) {
        var customer = customerRepository.findByPersonalId(personalId).orElseThrow();
        var request = new EvaluationRequest();
        request.setRequestedAmount(requestedAmount);
        request.setRequestedTerm(requestedTerm);
        request.setCustomer(customer);
        return evaluationRequestRepository.save(request).getId();
    }

}
