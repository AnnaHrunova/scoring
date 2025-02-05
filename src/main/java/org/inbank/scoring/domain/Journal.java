package org.inbank.scoring.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "journal", schema = ScoringSchema.NAME)
public class Journal extends BaseEntity {

    @ManyToOne
    @JoinColumn(
            name = "customer_id_journal",
            foreignKey = @ForeignKey(name = "fk_journal_customer"),
            nullable = false)
    private Customer customer;

    @Column
    private BigDecimal amount;

    @Column
    private Integer term;

    @Column(name = "is_approve")
    private boolean isApprove;

    @Column()
    private String details;
}
