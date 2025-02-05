package org.inbank.scoring.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.inbank.scoring.service.JournalService;
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

    private final JournalService journalService;

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
        journalService.prepareCustomer("112233-12345", 50);
        return "redirect:/approve";
    }
}
