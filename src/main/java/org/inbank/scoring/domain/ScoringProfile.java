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

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(
            name = "customer_id_scoring",
            foreignKey = @ForeignKey(name = "fk_scoring_customer"),
            nullable = false)
    private Customer customer;
}
