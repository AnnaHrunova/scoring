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

    @OneToOne
    @JoinColumn(
            name = "customer_id_scoring",
            foreignKey = @ForeignKey(name = "fk_customer_scoring"),
            nullable = false)
    private ScoringProfile scoringProfile;

}
