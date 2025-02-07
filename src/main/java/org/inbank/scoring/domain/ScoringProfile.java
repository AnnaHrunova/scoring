package org.inbank.scoring.domain;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "scoring_profile", schema = ScoringSchema.NAME)
public class ScoringProfile extends BaseEntity {

    @Column(name = "financial_capacity")
    private Integer financialCapacity;

    @Column(name = "is_ineligible")
    private boolean isIneligible;

}
