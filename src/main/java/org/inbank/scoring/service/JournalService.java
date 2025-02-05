package org.inbank.scoring.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.val;
import org.inbank.scoring.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class JournalService {

    private final CustomerRepository customerRepository;
    private final ScoringProfileRepository scoringProfileRepository;
    private final JournalRepository journalRepository;

    @Transactional
    public UUID prepareCustomer(String personalId, Integer financialFactor) {

        var customer = new Customer();
        customer.setPersonalId(personalId);
        var savedCustomer = customerRepository.save(customer);

        var scoringProfile = new ScoringProfile();
        scoringProfile.setCustomer(customer);
        scoringProfile.setFinancialCapacity(financialFactor);
        scoringProfile.setActive(true);

        scoringProfileRepository.save(scoringProfile);

        return savedCustomer.getId();
    }

    private Optional<Integer> getActiveFinancialFactor(String personalId) {
        return scoringProfileRepository.findFirstByCustomerPersonalIdOrderByCreatedDateDesc(personalId)
                .filter(ScoringProfile::isActive)
                .map(ScoringProfile::getFinancialCapacity);
    }

}
