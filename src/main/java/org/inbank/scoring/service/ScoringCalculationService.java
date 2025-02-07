package org.inbank.scoring.service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.inbank.scoring.api.PurchaseApprovalRequest;
import org.inbank.scoring.api.PurchaseApprovalResult;
import org.inbank.scoring.external.FinancialFactorProvider;
import org.inbank.scoring.external.ScoreProvider;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ScoringCalculationService {

    private final FinancialFactorProvider financialFactorProvider;

    private final EvaluationRequestService evaluationRequestService;

    private final ScoreProvider scoreProvider;

    public PurchaseApprovalResult evaluate(@Valid PurchaseApprovalRequest request) {
        var personId = request.getPersonId();

        if (evaluationRequestService.isCustomerIneligible(request.getPersonId())) {
            log.error("Ineligible customer with personId={}", personId);
            throw new RuntimeException(String.format("Ineligible customer with personId=%s", personId));
        }

        int financialFactor;
        var currentFinancialFactor = evaluationRequestService.getCustomerFinancialFactor(personId);

        if (currentFinancialFactor.isPresent()) {
            financialFactor = currentFinancialFactor.get();
        } else {
            financialFactor = financialFactorProvider.getFinancialFactor(personId);
            evaluationRequestService.prepareCustomer(personId, financialFactor);
        }
        var amount = Integer.parseInt(request.getAmount());
        var term = Integer.parseInt(request.getTerm());
        var requestUUID = evaluationRequestService.savePurchaseApprovalRequest(personId, amount, term);
        var evaluationResult = scoreProvider.evaluate(financialFactor, amount, term);
       evaluationRequestService.finishPurchaseApprovalRequest(requestUUID, evaluationResult);

       return evaluationResult;

    }
}
