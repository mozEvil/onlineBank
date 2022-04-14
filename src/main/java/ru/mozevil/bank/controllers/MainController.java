package ru.mozevil.bank.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mozevil.bank.entities.Account;
import ru.mozevil.bank.entities.AccountType;
import ru.mozevil.bank.entities.User;
import ru.mozevil.bank.services.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;

    @GetMapping
    public String home() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/userFront")
    public String userFront(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        Account primaryAccount = user.getAccounts().stream()
                .filter(o -> o.getType().equals(AccountType.PRIMARY))
                .findFirst().orElse(null);
        Account savingsAccount = user.getAccounts().stream()
                .filter(o -> o.getType().equals(AccountType.SAVINGS))
                .findFirst().orElse(null);
        
        model.addAttribute("primaryAccount", primaryAccount);
        model.addAttribute("savingsAccount", savingsAccount);

        return "userFront";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "signup";
    }

    @PostMapping("/signup")
    public String signupPost(@ModelAttribute("user") User user, Model model) {
        if (userService.checkUserExists(user.getUsername(), user.getEmail())) {
            if (userService.checkEmailExists(user.getEmail())) {
                model.addAttribute("emailExists", true);
            }
            if (userService.checkUsernameExists(user.getUsername())) {
                model.addAttribute("usernameExists", true);
            }
            return "signup";
        }
        userService.createUser(user);

        return "redirect:/";
    }


}
