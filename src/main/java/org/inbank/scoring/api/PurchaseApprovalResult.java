package org.inbank.scoring.api;

import java.math.BigDecimal;

public record PurchaseApprovalResult(Integer approvedAmount,
                                     Integer approvedTerm,
                                     String message,
                                     BigDecimal approvalScore,
                                     boolean isIneligibleCustomer
) { }
