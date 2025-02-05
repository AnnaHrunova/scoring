package org.inbank.scoring.service;

import org.inbank.scoring.config.BaseIntegrationTest;
import org.inbank.scoring.domain.CustomerRepository;
import org.inbank.scoring.domain.ScoringProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JournalServiceTest extends BaseIntegrationTest {

    @Autowired
    private JournalService subject;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ScoringProfileRepository scoringProfileRepository;

    @Test
    void shouldInitializeCustomerWithScoringProfile() {
        var customerPersonalId = "112233-56789";
        var newCustomer = subject.prepareCustomer(customerPersonalId, 100);

        var savedCustomer = customerRepository.findById(newCustomer);
        var savedScoringProfile = scoringProfileRepository.findFirstByCustomerPersonalIdOrderByCreatedDateDesc(customerPersonalId);

        assertTrue(savedCustomer.isPresent());
        assertTrue(savedScoringProfile.isPresent());
        assertEquals(100, savedScoringProfile.get().getFinancialCapacity());
    }

}