package org.inbank.scoring.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
