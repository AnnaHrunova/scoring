package org.inbank.scoring.domain;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer", schema = ScoringSchema.NAME)
public class Customer extends BaseEntity{

    @Column(name = "personal_id", unique = true)
    private String personalId;

    @Column(name = "is_ineligible")
    private boolean isIneligible;
}
