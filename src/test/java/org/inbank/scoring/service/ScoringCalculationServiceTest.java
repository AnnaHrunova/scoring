package org.inbank.scoring.service;

import org.inbank.scoring.api.PurchaseApprovalRequest;
import org.inbank.scoring.config.BaseIntegrationTest;
import org.inbank.scoring.domain.EvaluationRequestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoringCalculationServiceTest extends BaseIntegrationTest {

    @Autowired
    private EvaluationRequestRepository evaluationRequestRepository;

    @Autowired
    private EvaluationRequestService evaluationRequestService;

    @Autowired
    private ScoringService scoringCalculationService;

    @Test
    void shouldTestParallelExecution() throws InterruptedException {
        var personId = "121212-12345";

        var request = new PurchaseApprovalRequest(personId, "500", "10");
        evaluationRequestService.prepareCustomer(personId, 100);
        Callable<Object> createTrx = () -> scoringCalculationService.evaluate(request);

        try (var executorService = Executors.newFixedThreadPool(6)) {

            executorService.invokeAll(List.of(
                    createTrx, createTrx, createTrx,
                    createTrx, createTrx, createTrx
            ));

            executorService.shutdown();
        }

        assertEquals(1, evaluationRequestRepository.findAllByCustomerPersonId(personId).size());
    }

}