package org.inbank.scoring.service;


import lombok.AllArgsConstructor;
import org.inbank.scoring.external.FinancialFactorProvider;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScoringCalculationService {

    private final FinancialFactorProvider financialFactorProvider;

    private final JournalService journalService;
}
