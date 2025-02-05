package org.inbank.scoring.api;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseApprovalRequest {

    @NotNull
    @DecimalMin(value = "200.00")
    @DecimalMax(value = "5000.00")
    @Digits(integer=4, fraction=2)
    private BigDecimal amount;

    @NotNull
    @Range(min = 6, max = 24)
    private Integer term;
}
