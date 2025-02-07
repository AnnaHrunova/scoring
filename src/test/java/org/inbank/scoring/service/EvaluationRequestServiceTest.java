package org.inbank.scoring.service;

import org.inbank.scoring.config.BaseIntegrationTest;
import org.inbank.scoring.domain.CustomerRepository;
import org.inbank.scoring.domain.ScoringProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EvaluationRequestServiceTest extends BaseIntegrationTest {

    @Autowired
    private EvaluationRequestService subject;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ScoringProfileRepository scoringProfileRepository;

    @Test
    void shouldInitializeCustomerWithScoringProfile() {
        var customerPersonalId = "112233-56789";
        var newCustomer = subject.prepareCustomer(customerPersonalId, 100);

        var savedCustomer = customerRepository.findById(newCustomer);
        assertTrue(savedCustomer.isPresent());

        var savedScoringProfile = savedCustomer.get().getScoringProfile();
        assertEquals(100, savedScoringProfile.getFinancialCapacity());
    }

}