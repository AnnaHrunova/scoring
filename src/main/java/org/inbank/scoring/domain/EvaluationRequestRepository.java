package org.inbank.scoring.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EvaluationRequestRepository extends JpaRepository<EvaluationRequest, UUID> {

    List<EvaluationRequest> findAllByCustomerPersonId(String personId);
    Optional<EvaluationRequest> findFirstByCustomerPersonIdOrderByCreatedDateDesc(String personId);
}
