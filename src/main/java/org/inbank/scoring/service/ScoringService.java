package org.inbank.scoring.service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.inbank.scoring.api.PurchaseApprovalRequest;
import org.inbank.scoring.api.PurchaseApprovalResult;
import org.inbank.scoring.external.CustomerPersonalChecksProvider;
import org.inbank.scoring.external.FinancialFactorProvider;
import org.inbank.scoring.external.ScoreCalculatorProvider;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ScoringService {

    private final FinancialFactorProvider financialFactorProvider;

    private CustomerPersonalChecksProvider customerPersonalChecksProvider;

    private final EvaluationRequestService evaluationRequestService;

    private final ScoreCalculatorProvider scoreProvider;

    //avoid the case when multiple purchase requests are approved simultaneously
    public synchronized PurchaseApprovalResult evaluate(@Valid PurchaseApprovalRequest request) {
        var personId = request.getPersonId();

        if (evaluationRequestService.isCustomerIneligible(request.getPersonId())) {
            log.error("Ineligible customer with personId={}", personId);
            return new PurchaseApprovalResult(null, null, null, null, true);
        }

        var currentApprovalResult = evaluationRequestService.hasActiveRequest(personId);
        if (currentApprovalResult != null) {
            log.info("Customer with personId={} already has an active purchase approval request", personId);
            return currentApprovalResult;
        }

        int financialFactor;
        var currentFinancialFactor = evaluationRequestService.getCustomerFinancialFactor(personId);

        if (currentFinancialFactor.isPresent()) {
            financialFactor = currentFinancialFactor.get();
        } else {
            var isCustomerIneligible = customerPersonalChecksProvider.isIneligible(personId);
            if (isCustomerIneligible) {
                evaluationRequestService.prepareIneligibleCustomer(personId);
                log.error("Ineligible customer with personId={}", personId);
                return new PurchaseApprovalResult(null, null, null, null, true);
            } else {
                financialFactor = financialFactorProvider.getFinancialFactor(personId);
                evaluationRequestService.prepareCustomer(personId, financialFactor);
            }
        }
        var amount = Integer.parseInt(request.getAmount());
        var term = Integer.parseInt(request.getTerm());
        var requestUUID = evaluationRequestService.savePurchaseApprovalRequest(personId, amount, term);
        var evaluationResult = scoreProvider.evaluate(financialFactor, amount, term);
        evaluationRequestService.finishPurchaseApprovalRequest(requestUUID, evaluationResult);

        return evaluationResult;
    }
}
