package org.inbank.scoring.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.inbank.scoring.service.ScoringService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/scoring")
@AllArgsConstructor
public class ScoringController {

    private final ScoringService scoringCalculationService;

    @GetMapping
    public String init(Model model) {
        model.addAttribute("request", new PurchaseApprovalRequest());
        return "index";
    }

    @PostMapping("/calculate")
    public String calculate(@Valid @ModelAttribute("request") PurchaseApprovalRequest request, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "index";
        }
        var scoringResult = scoringCalculationService.evaluate(request);
        prepareView(model, scoringResult);
        return "result";
    }

    private static void prepareView(Model model, PurchaseApprovalResult response) {
        if (!response.isIneligibleCustomer()) {
            model.addAttribute("approvedAmount", response.approvedAmount());
            model.addAttribute("approvedTerm", response.approvedTerm());
            model.addAttribute("approvalScore", response.approvalScore());
            model.addAttribute("message", response.message());
        }
        model.addAttribute("isIneligible", response.isIneligibleCustomer());
    }
}
