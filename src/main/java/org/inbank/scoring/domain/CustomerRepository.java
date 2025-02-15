package org.inbank.scoring.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID>  {

    Optional<Customer> findByPersonId(String personId);
}
