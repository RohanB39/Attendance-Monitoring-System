package com.ecom.controller;

import com.ecom.model.User;
import com.ecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("email") String email, @RequestParam("phoneNumber") Long phoneNumber, RedirectAttributes redirectAttributes) {
        User newUser = new User(username, password, email, phoneNumber);
        boolean Saving = userService.saveUser(newUser);
        if (Saving == true) {
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Registration failed. Please try again.");
            return "redirect:/register";
        }
    }
}
