package org.inbank.scoring.api;

import java.math.BigDecimal;

public record PurchaseApprovalResult(Integer amount,
                                     Integer term,
                                     String result,
                                     BigDecimal approvalScore
) { }
