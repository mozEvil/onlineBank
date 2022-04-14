package ru.mozevil.bank.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mozevil.bank.entities.Account;
import ru.mozevil.bank.entities.AccountType;
import ru.mozevil.bank.entities.Recipient;
import ru.mozevil.bank.entities.User;
import ru.mozevil.bank.services.AccountService;
import ru.mozevil.bank.services.RecipientService;
import ru.mozevil.bank.services.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {
    private final UserService userService;
    private final AccountService accountService;
    private final RecipientService recipientService;


    @GetMapping("/betweenAccounts")
    public String betweenAccounts(Model model) {
        model.addAttribute("transferFrom", "");
        model.addAttribute("transferTo", "");
        model.addAttribute("amount", "");
        return "betweenAccounts";
    }

    @PostMapping("/betweenAccounts")
    public String betweenAccountsPost(@ModelAttribute("transferFrom") String transferFrom,
                                      @ModelAttribute("transferTo") String transferTo,
                                      @ModelAttribute("amount") Double amount,
                                      Principal principal) throws Exception {
        Account from = accountService.getAccount(principal.getName(), AccountType.valueOf(transferFrom.toUpperCase()));
        Account to = accountService.getAccount(principal.getName(), AccountType.valueOf(transferTo.toUpperCase()));
        accountService.transferFromTo(from, to, amount);
        return "redirect:/userFront";
    }

    @GetMapping("/recipient")
    public String recipient(Model model, Principal principal) {
        List<Recipient> recipientList = userService.findByUsername(principal.getName()).getRecipients();
        Recipient recipient = new Recipient();
        model.addAttribute("recipientList", recipientList);
        model.addAttribute("recipient", recipient);
        return "recipient";
    }

    @PostMapping("/recipient/save")
    public String recipientPost(@ModelAttribute("recipient") Recipient recipient, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        recipient.setUser(user);
        recipientService.saveRecipient(recipient);
        return "redirect:/transfer/recipient";
    }

    @GetMapping("/recipient/edit")
    public String recipientEdit(@RequestParam(value = "recipientName") String recipientName, Model model, Principal principal){
        Recipient recipient = recipientService.findRecipientByName(recipientName);
        List<Recipient> recipientList = recipientService.findRecipientList(principal.getName());
        model.addAttribute("recipientList", recipientList);
        model.addAttribute("recipient", recipient);
        return "recipient";
    }

    @Transactional
    @RequestMapping(value = "/recipient/delete", method = RequestMethod.GET)
    public String recipientDelete(@RequestParam(value = "recipientName") String recipientName, Model model, Principal principal){
        recipientService.deleteRecipientByName(recipientName);
        List<Recipient> recipientList = recipientService.findRecipientList(principal.getName());
        Recipient recipient = new Recipient();
        model.addAttribute("recipient", recipient);
        model.addAttribute("recipientList", recipientList);
        return "recipient";
    }

    @RequestMapping(value = "/toSomeoneElse",method = RequestMethod.GET)
    public String toSomeoneElse(Model model, Principal principal) {
        List<Recipient> recipientList = recipientService.findRecipientList(principal.getName());
        model.addAttribute("recipientList", recipientList);
        model.addAttribute("accountType", "");
        return "toSomeoneElse";
    }

    @RequestMapping(value = "/toSomeoneElse",method = RequestMethod.POST)
    public String toSomeoneElsePost(@ModelAttribute("recipientName") String recipientName,
                                    @ModelAttribute("accountType") String accountType,
                                    @ModelAttribute("amount") Double amount, Principal principal) {
        Recipient recipient = recipientService.findRecipientByName(recipientName);
        Account from = accountService.getAccount(principal.getName(), AccountType.valueOf(accountType.toUpperCase()));
        accountService.toSomeoneElseTransfer(recipient, from, amount);
        return "redirect:/userFront";
    }

}
