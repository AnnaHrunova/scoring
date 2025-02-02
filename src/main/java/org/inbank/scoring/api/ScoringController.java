package org.inbank.scoring.api;

import jakarta.validation.Valid;
import org.inbank.scoring.domain.PurchaseApprovalRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller("scoring")
public class ScoringController {

    @GetMapping
    public String init(Model model) {
        model.addAttribute("request", new PurchaseApprovalRequest());
        return "index";
    }

    @PostMapping("/calculagte")
    public String addUser(@Valid PurchaseApprovalRequest request, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "index";
        }

        return "redirect:/approve";
    }
}
