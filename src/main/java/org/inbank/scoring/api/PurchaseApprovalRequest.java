package org.inbank.scoring.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.inbank.scoring.service.validator.AmountConstraint;
import org.inbank.scoring.service.validator.PersonIdConstraint;
import org.inbank.scoring.service.validator.TermConstraint;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseApprovalRequest {

    @PersonIdConstraint
    private String personId;

    @AmountConstraint
    private String amount;

    @TermConstraint
    private String term;
}
