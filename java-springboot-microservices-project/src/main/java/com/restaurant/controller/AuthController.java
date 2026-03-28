package com.restaurant.controller;

import com.restaurant.model.User;
import com.restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) model.addAttribute("error", "Invalid email or password!");
        if (logout != null) model.addAttribute("message", "You have been logged out.");
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        if (userRepo.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email already registered! Please login.");
            return "register";
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("CUSTOMER");
        userRepo.save(user);
        return "redirect:/login?registered";
    }
}
