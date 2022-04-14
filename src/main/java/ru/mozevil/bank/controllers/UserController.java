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
import ru.mozevil.bank.entities.User;
import ru.mozevil.bank.services.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        Account primaryAccount = user.getAccounts().stream()
                .filter(o -> o.getType().equals(AccountType.PRIMARY))
                .findFirst().orElse(null);
        Account savingsAccount = user.getAccounts().stream()
                .filter(o -> o.getType().equals(AccountType.SAVINGS))
                .findFirst().orElse(null);

        model.addAttribute("user", user);
        model.addAttribute("primaryAccountNumber", primaryAccount.getAccountNumber());
        model.addAttribute("savingsAccountNumber", savingsAccount.getAccountNumber());
        return "profile";
    }

    @PostMapping("/profile")
    public String profilePost(@ModelAttribute("user") User newUser, Model model) {
        User user = userService.findByUsername(newUser.getUsername());
        user.setUsername(newUser.getUsername());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        user.setPhone(newUser.getPhone());

        model.addAttribute("user", user);

        userService.saveUser(user);

        return "profile";
    }


}
