package org.inbank.scoring.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ScoringProfileRepository extends JpaRepository<ScoringProfile, UUID> {

    Optional<ScoringProfile> findFirstByCustomerPersonalIdOrderByCreatedDateDesc(String personalId);
}
