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
@Table(name = "evaluation_request", schema = ScoringSchema.NAME)
public class EvaluationRequest extends BaseEntity {

    @ManyToOne
    @JoinColumn(
            name = "customer_id_request",
            foreignKey = @ForeignKey(name = "fk_request_customer"),
            nullable = false)
    private Customer customer;

    @Column(name = "requested_amount", nullable = false)
    private BigDecimal requestedAmount;

    @Column(name = "requested_term", nullable = false)
    private Integer requestedTerm;

    @Column(name = "approved_amount")
    private BigDecimal approvedAmount;

    @Column(name = "approved_term")
    private Integer approvedTerm;

    @Column
    @Enumerated(EnumType.STRING)
    private ScoringResult result;

    @Column(name = "approval_score")
    private BigDecimal approvalScore;
}
