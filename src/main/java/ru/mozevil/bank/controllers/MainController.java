package ru.mozevil.bank.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mozevil.bank.entities.User;

import java.util.HashMap;
import java.util.HashSet;

@Controller
public class MainController {

    @GetMapping
    public String home() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "signup";
    }

    @PostMapping("/signup")
    public String signupPost(@ModelAttribute("user") User user, Model model) {
// проверить на наличие юзернейма и емейла
        //        if (userService.checkUserExists(user.getUsername(), user.getEmail())) {
//            if (userService.checkEmailExists(user.getEmail())) {
//                model.addAttribute("emailExists", true);
//            }
//            if (userService.checkUsernameExists(user.getUsername())) {
//                model.addAttribute("usernameExists", true);
//            }
//            return "signup";
//        }
        // если все ок то добавить юзера в базу с ролью ЮЗЕР


        return "redirect:/";
    }


}
