package ru.mozevil.bank.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mozevil.bank.entities.Account;
import ru.mozevil.bank.entities.AccountType;
import ru.mozevil.bank.entities.Transaction;
import ru.mozevil.bank.services.AccountService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;


    @GetMapping("/primaryAccount")
    public String primaryAccount(Model model, Principal principal) {
        Account primaryAccount = accountService.getAccount(principal.getName(), AccountType.PRIMARY);
        List<Transaction> primaryTransactionList = primaryAccount.getTransactions();
        model.addAttribute("primaryAccount", primaryAccount);
        model.addAttribute("primaryTransactionList", primaryTransactionList);
        return "primaryAccount";
    }

    @GetMapping("/savingsAccount")
    public String savingsAccount(Model model, Principal principal) {
        Account savingsAccount = accountService.getAccount(principal.getName(), AccountType.SAVINGS);
        List<Transaction> savingsTransactionList = savingsAccount.getTransactions();
        model.addAttribute("savingsAccount", savingsAccount);
        model.addAttribute("savingsTransactionList", savingsTransactionList);
        return "savingsAccount";
    }

    @GetMapping("/deposit")
    public String deposit(Model model) {
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");
        return "deposit";
    }

    @PostMapping("/deposit")
    public String depositPOST(@ModelAttribute("amount") Double amount,
                              @ModelAttribute("accountType") String accountType, Principal principal) {
        AccountType type = AccountType.valueOf(accountType.toUpperCase());
        Account account = accountService.getAccount(principal.getName(), type);
        accountService.deposit(account, amount);
        return "redirect:/userFront";
    }

    @GetMapping("/withdraw")
    public String withdraw(Model model) {
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");
        return "withdraw";
    }

    @PostMapping("/withdraw")
    public String withdrawPOST(@ModelAttribute("amount") Double amount,
                               @ModelAttribute("accountType") String accountType, Principal principal) {
        AccountType type = AccountType.valueOf(accountType.toUpperCase());
        Account account = accountService.getAccount(principal.getName(), type);
        accountService.withdraw(account, amount);
        return "redirect:/userFront";
    }

}
